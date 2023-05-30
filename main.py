from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse
import tensorflow as tf
import io
import os
import uvicorn

app = FastAPI()

model = tf.keras.models.load_model("model.h5")

classification_result = None  # Definisikan variabel classification_result

def classify_image(image):
    # Preprocess the image if needed
    # Perform classification using the model
    predictions = model.predict(image)
    # Process the predictions if needed
    return predictions


@app.post("/upload-photo")
async def upload_photo(file: UploadFile = File(...)):
    contents = await file.read()
    image = tf.image.decode_image(contents, channels=3)
    image = tf.image.resize(image, [224, 224])

    predictions = classify_image(tf.expand_dims(image, axis=0))

    global classification_result
    classification_result = predictions.tolist()  # Simpan hasil klasifikasi ke dalam classification_result

    return JSONResponse(content={"predictions": predictions.tolist()})


@app.get("/classification-result")
async def get_classification_result():
    global classification_result

    if classification_result:
        result = classification_result
        classification_result = None
        return JSONResponse(content={"result": result})
    else:
        return JSONResponse(content={"result": None})


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
