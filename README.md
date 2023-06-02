# API Classification Result

This API allows you to upload an image, classify it using a pre-trained model, and retrieve the classification result.

## Installation

1. Clone the repository: 
```sheel
git clone -b Recyclass-API https://github.com/imyanuarginting/Recyclass.git
 ```
2. Install the required dependencies:
```sheel
pip install -r requirements.txt
```

3. Download the pre-trained model weights (`model.h5`) and place it in the same directory as `main.py`.

## Usage

1. Start the server:
```sheel
python main.py
```


2. Use the following endpoints:

- **POST /upload-photo**: Upload an image for classification. The image should be sent as a form-data request with the key `file`.
- **GET /classification-result**: Get the classification result. This endpoint will return the highest predicted label from the last uploaded image.
- **GET /location**: Get the location of the Garbage Bank. This endpoint will return a list of Garbage Bank locations by region.
## Example

Here is an example of how to use the API using cURL:

1. Upload an image:
```sheel
curl -X POST -F "file=@image.jpg" http://localhost:8000/upload-photo
```

2. Get the classification result:
```sheel
curl http://localhost:8000/classification-result
```

The response will contain the highest predicted label for the uploaded image.

## Notes

- The API uses TensorFlow and a pre-trained model (`model.h5`) for image classification. Make sure to download the model weights and place them in the same directory as `main.py`.
- The API is built with FastAPI, a modern, fast (high-performance) web framework for building APIs with Python.
- The API assumes that the uploaded images are in RGB format and have a resolution of 224x224 pixels. You may need to preprocess the images accordingly if they don't meet these requirements.

Feel free to modify the API according to your needs. Enjoy classifying images!

# API Classification Result

This API allows you to upload an image, classify it using a pre-trained model, and retrieve the classification result.

## License

This project is licensed under the terms of the Team C23-PS296 - Capstone Project.






