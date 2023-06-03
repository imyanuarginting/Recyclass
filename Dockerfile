FROM python:3.8.10

# Set working directory
WORKDIR /app

# Copy requirements.txt for installation dependencies
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy file main.py to container
COPY main.py .

# Copy file model.h5 to container
COPY model.h5 .

# Copy file article.json to container
COPY articles.json .

# Copy file tempat.json ke dalam container
COPY locations.json .

# Menjalankan aplikasi FastAPI pada port 8080
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
