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
    if len(content) > 1024 * 1024:  # 1MB
        error_message = "Ukuran gambar terlalu besar, maksimal 1MB."
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail={"error": True, "message": error_message})

    # Check image type
    allowed_types = ["image/jpeg", "image/png"]
    if file.content_type not in allowed_types:
        error_message = "Tipe gambar tidak didukung. Gunakan format JPEG atau PNG."
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail={"error": True, "message": error_message})

    image = tf.image.decode_image(content, channels=3)
    image = tf.image.resize(image, [224, 224])

    predictions = classify_image(tf.expand_dims(image, axis=0))

    global classification_result
    classification_result = predictions.tolist()

    return JSONResponse(content={"predictions": predictions.tolist()})

@app.get("/classification-result")
async def get_classification_result():
    global classification_result

    if classification_result:
        result = get_highest_label(classification_result)
        classification_result = None
        return JSONResponse(content={"result": result})
    else:
        error_message = "Hasil klasifikasi tidak tersedia."
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail={"error": True, "message": error_message})

@app.get("/lokasi/{city}")
async def get_city_data(city: str):
    file_path = "tempat.json"  # Ubah sesuai dengan path file JSON Anda
    with open(file_path) as file:
        data = json.load(file)

    if city in data:
        result = data[city]
        return JSONResponse(content=result)
    else:
        error_message = "Lokasi Bank Sampah tidak ditemukan."
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail={"error": True, "message": error_message})

#@app.get("/artikel")
#async def get_article():
#    file_path = "artikel.json"  # Ubah sesuai dengan path file JSON artikel Anda
#    with open(file_path) as file:
#        data = json.load(file)
#    
#    return JSONResponse(content=data)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)