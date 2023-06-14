from fastapi import FastAPI, File, UploadFile, HTTPException, status
from fastapi.responses import JSONResponse
import tensorflow as tf
import io
import os
import uvicorn
import json

app = FastAPI()

model_path = os.path.join(os.path.dirname(__file__), 'model.h5')
model = tf.keras.models.load_model(model_path)

classification_result = None
labels = ["HDPE", "LDPE", "OTHERS", "PET", "PP", "PS", "PVC"]

def classify_image(image):
    # Preprocess the image if needed
    # Perform classification using the model
    predictions = model.predict(image)
    # Process the predictions if needed
    return predictions

def get_highest_label(predictions):
    highest_index = tf.argmax(predictions, axis=1)[0]
    return labels[highest_index]

@app.post("/upload-photo")
async def upload_photo(file: UploadFile = File(...)):
    # Check image size
    content = await file.read()
    if len(content) > 10 * 1024 * 1024:  # 10MB
        error_message = "Image size is too large. Maximum allowed size is 10MB."
        return JSONResponse(content={"error": True,"message": error_message})

    # Check image type
    allowed_types = ["image/jpeg", "image/png"]
    if file.content_type not in allowed_types:
        error_message = "Unsupported image type. Please use JPEG or PNG format."
        return JSONResponse(content={"error": True,"message": error_message})

    image = tf.image.decode_image(content, channels=3)
    image = tf.image.resize(image, [224, 224])

    predictions = classify_image(tf.expand_dims(image, axis=0))

    global classification_result
    classification_result = predictions.tolist()

    return JSONResponse(content={"error": False,"message": "Success to Upload"})


@app.get("/classification-result")
async def get_classification_result():
    global classification_result

    if classification_result:
        highest_prediction = max(classification_result[0])
        if highest_prediction < 0.8:
            classification_result = None
            error_message = "No Plastic Found"
            return JSONResponse(content={"error": False, "result": error_message})
        else:
            result = get_highest_label(classification_result)
            classification_result = None
            return JSONResponse(content={"error": False, "result": result})
    else:
        error_message = "Classification result is not available."
        return JSONResponse(content={"error": True, "message": error_message})

@app.get("/locations/")
async def get_city_data(city: str):
    file_path = "locations.json"  # Replace with the correct path to your JSON file
    with open(file_path) as file:
        data = json.load(file)

    city = city.lower()  # Convert city to lowercase

    if city in data:
        result = data[city]
        return JSONResponse(content={"error": False, "result": result})
    else:
        error_message = "Location not found."
        return JSONResponse(content={"error": True, "message": error_message})

@app.get("/articles/")
async def get_articles_data(plastic_type: str, page: int = 1, size: int = 10):
    file_path = "articles.json"  # Replace with the correct path to your JSON file
    with open(file_path) as file:
        data = json.load(file)

    plastic_type = plastic_type.upper()  # Convert to uppercase

    if plastic_type in data:
        articles = data[plastic_type]
        #total_articles = len(articles)
        start_index = (page - 1) * size
        end_index = start_index + size

        result = articles[start_index:end_index]

        if len(result) == 0:
            return JSONResponse(content={"error": True, "result": []})

        return JSONResponse(content={"error": False, "result": result})
    else:
        error_message = "Wrong Plastic Type"
        return JSONResponse(content={"error": True, "message": error_message})




if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
