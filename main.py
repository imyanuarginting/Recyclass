from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse
import tensorflow as tf
import io
import os
import uvicorn
import json

app = FastAPI()

model_path = os.path.join(os.path.dirname(__file__), 'model1.h5')
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

#endpoints for posting image to server
@app.post("/upload-photo")
async def upload_photo(file: UploadFile = File(...)):
     contents = await file.read()
     image = tf.image.decode_image(contents, channels=3)
     image = tf.image.resize(image, [224, 224])

     predictions = classify_image(tf.expand_dims(image, axis=0))

     global classification_result
     classification_result = predictions.tolist()  # Simpan hasil klasifikasi ke dalam classification_result.

     return JSONResponse(content={"predictions": predictions.tolist()})

# endpoint for getting data from server 
@app.get("/classification-result") 
async def get_classification_result():
    global classification_result

    if classification_result:
        result = get_highest_label(classification_result)
        classification_result = None
        return JSONResponse(content={"result": result})
    else:
        return JSONResponse(content={"result": None})


@app.get("/lokasi/{city}")
async def get_city_data(city: str):
    file_path = "tempat.json"  # Ubah sesuai dengan path file JSON Anda
    with open(file_path) as file:
        data = json.load(file)
    
    if city in data:
        result = data[city]
        return JSONResponse(content=result)
    else:
        return JSONResponse(content={"error": "Lokasi Bank Sampah tidak ditemukan"})

@app.get("/artikel")
async def get_article():
    file_path = "artikel.json"  # Ubah sesuai dengan path file JSON artikel Anda
    with open(file_path) as file:
        data = json.load(file)
    
    return JSONResponse(content=data)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)