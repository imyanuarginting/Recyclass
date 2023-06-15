# Build Images Classification Using Transfer Learning (MobileNetV2)

Following are the steps for building an image classification using Transfer Learning (MobileNetV2)

## Steps

1. Download Recyclass.zip from this repository
```
!git clone https://github.com/imyanuarginting/Recyclass.git
```
2. Extract Recyclass.zip file from the repository downloaded previously
```
import os
import zipfile

zip_ref = zipfile.ZipFile('Recyclass/Recyclass.zip', 'r')
zip_ref.extractall("tmp/")
zip_ref.close()

base_dir = 'tmp/Recyclass'
```
3. Import some libraries to check whether TensorFlow supports the format of the images or not
```
from pathlib import Path
import imghdr
```
4. Check the format of the images supported by TensorFlow
```
train_dir = os.path.join(base_dir, 'train')
image_extensions = ['.png', '.jpg']

img_type_accepted = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
for filepath in Path(train_dir).rglob("*"):
  if filepath.suffix.lower() in image_extensions:
    img_type = imghdr.what(filepath)
    if img_type is None:
      print(f'{filepath} is not an image')
    elif img_type not in img_type_accepted:
      print(f'{filepath} is a {img_type}, not accepted by TensorFlow')
```
```
val_dir = os.path.join(base_dir, 'val')
image_extensions = ['.png', '.jpg']

img_type_accepted = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
for filepath in Path(val_dir).rglob("*"):
  if filepath.suffix.lower() in image_extensions:
    img_type = imghdr.what(filepath)
    if img_type is None:
      print(f'{filepath} is not an image')
    elif img_type not in img_type_accepted:
      print(f'{filepath} is a {img_type}, not accepted by TensorFlow')
```
5. Check the total of the training and validation images
```
train_dir = os.path.join(base_dir, 'train')
val_dir = os.path.join(base_dir, 'val')
```
```
hdpe_train_dir = os.path.join(train_dir, 'hdpe')
ldpe_train_dir = os.path.join(train_dir, 'ldpe')
others_train_dir = os.path.join(train_dir, 'others')
pet_train_dir = os.path.join(train_dir, 'pet')
pp_train_dir = os.path.join(train_dir, 'pp')
ps_train_dir = os.path.join(train_dir, 'ps')
pvc_train_dir = os.path.join(train_dir, 'pvc')
```
```
hdpe_val_dir = os.path.join(val_dir, 'hdpe')
ldpe_val_dir = os.path.join(val_dir, 'ldpe')
others_val_dir = os.path.join(val_dir, 'others')
pet_val_dir = os.path.join(val_dir, 'pet')
pp_val_dir = os.path.join(val_dir, 'pp')
ps_val_dir = os.path.join(val_dir, 'ps')
pvc_val_dir = os.path.join(val_dir, 'pvc')
```
```
print('total training HDPE images:', len(os.listdir(hdpe_train_dir)))
print('total training LDPE images:', len(os.listdir(ldpe_train_dir)))
print('total training Others images:', len(os.listdir(others_train_dir)))
print('total training PET images:', len(os.listdir(pet_train_dir)))
print('total training PP images:', len(os.listdir(pp_train_dir)))
print('total training PS images:', len(os.listdir(ps_train_dir)))
print('total training PVC images:', len(os.listdir(pvc_train_dir)))
```
```
print('total validation HDPE images:', len(os.listdir(hdpe_val_dir)))
print('total validation LDPE images:', len(os.listdir(ldpe_val_dir)))
print('total validation Others images:', len(os.listdir(others_val_dir)))
print('total validation PET images:', len(os.listdir(pet_val_dir)))
print('total validation PP images:', len(os.listdir(pp_val_dir)))
print('total validation PS images:', len(os.listdir(ps_val_dir)))
print('total validation PVC images:', len(os.listdir(pvc_val_dir)))
```
6. Check the name of the training and validation images
```
hdpe_train_files = os.listdir(hdpe_train_dir)
print(hdpe_train_files[:10])

ldpe_train_files = os.listdir(ldpe_train_dir)
print(ldpe_train_files[:10])

others_train_files = os.listdir(others_train_dir)
print(others_train_files[:10])

pet_train_files = os.listdir(pet_train_dir)
print(pet_train_files[:10])

pp_train_files = os.listdir(pp_train_dir)
print(pp_train_files[:10])

ps_train_files = os.listdir(ps_train_dir)
print(ps_train_files[:10])

pvc_train_files = os.listdir(pvc_train_dir)
print(pvc_train_files[:10])
```
```
hdpe_val_files = os.listdir(hdpe_val_dir)
print(hdpe_val_files[:10])

ldpe_val_files = os.listdir(ldpe_val_dir)
print(ldpe_val_files[:10])

others_val_files = os.listdir(others_val_dir)
print(others_val_files[:10])

pet_val_files = os.listdir(pet_val_dir)
print(pet_val_files[:10])

pp_val_files = os.listdir(pp_val_dir)
print(pp_val_files[:10])

ps_val_files = os.listdir(ps_val_dir)
print(ps_val_files[:10])

pvc_val_files = os.listdir(pvc_val_dir)
print(pvc_val_files[:10])
```
7. Check training and validation images
```
import matplotlib.pyplot as plt
import matplotlib.image as mpimg

nrows = 7
ncols = 7

pic_index = 0

fig = plt.gcf()
fig.set_size_inches(ncols * 7, nrows * 7)

pic_index += 7
hdpe = [os.path.join(hdpe_train_dir, fname) 
          for fname in hdpe_train_files[pic_index-7:pic_index]]
ldpe = [os.path.join(ldpe_train_dir, fname) 
          for fname in ldpe_train_files[pic_index-7:pic_index]]
others = [os.path.join(others_train_dir, fname) 
          for fname in others_train_files[pic_index-7:pic_index]]
pet = [os.path.join(pet_train_dir, fname) 
          for fname in pet_train_files[pic_index-7:pic_index]]
pp = [os.path.join(pp_train_dir, fname) 
          for fname in pp_train_files[pic_index-7:pic_index]]
ps = [os.path.join(ps_train_dir, fname) 
          for fname in ps_train_files[pic_index-7:pic_index]]
pvc = [os.path.join(pvc_train_dir, fname) 
          for fname in pvc_train_files[pic_index-7:pic_index]]

for i, img_path in enumerate(hdpe + ldpe + others + pet + pp + ps + pvc):
  sp = plt.subplot(nrows, ncols, i + 1)
  sp.axis('On')

  img = mpimg.imread(img_path)
  plt.imshow(img)

plt.show()
```
```
import matplotlib.pyplot as plt
import matplotlib.image as mpimg

nrows = 7
ncols = 7

pic_index = 0

fig = plt.gcf()
fig.set_size_inches(ncols * 7, nrows * 7)

pic_index += 7
hdpe = [os.path.join(hdpe_val_dir, fname) 
          for fname in hdpe_val_files[pic_index-7:pic_index]]
ldpe = [os.path.join(ldpe_val_dir, fname) 
          for fname in ldpe_val_files[pic_index-7:pic_index]]
others = [os.path.join(others_val_dir, fname) 
          for fname in others_val_files[pic_index-7:pic_index]]
pet = [os.path.join(pet_val_dir, fname) 
          for fname in pet_val_files[pic_index-7:pic_index]]
pp = [os.path.join(pp_val_dir, fname) 
          for fname in pp_val_files[pic_index-7:pic_index]]
ps = [os.path.join(ps_val_dir, fname) 
          for fname in ps_val_files[pic_index-7:pic_index]]
pvc = [os.path.join(pvc_val_dir, fname) 
          for fname in pvc_val_files[pic_index-7:pic_index]]

for i, img_path in enumerate(hdpe + ldpe + others + pet + pp + ps + pvc):
  sp = plt.subplot(nrows, ncols, i + 1)
  sp.axis('Off')

  img = mpimg.imread(img_path)
  plt.imshow(img)

plt.show()
```
8. Initialize base model using Transfer Learning (MobileNetV2)
```
from tensorflow.keras.applications import MobileNetV2

base_model = MobileNetV2(weights='imagenet',
                       include_top=False,
                       input_shape=(224, 224, 3))

base_model.trainable = False

base_model.summary()
```
9. Create variables to make it easy to set hyperparameters
```
DENSE_LAYER = 32
DROPOUT_LAYER = 0.1
```
10. Add some layers in the output layer section
```
from tensorflow.keras.models import Model, Sequential
from tensorflow.keras import layers

model = Sequential([
    layers.Rescaling(1.0/255.0, input_shape=(224, 224, 3)),
    layers.RandomFlip(mode='horizontal'),
    base_model,
    layers.Flatten(),
    layers.Dense(DENSE_LAYER, activation='relu'),
    layers.Dropout(DROPOUT_LAYER),
    layers.Dense(7, activation='softmax')
])

model.summary()
```
11. Initialize optimizer, loss, and metrics
```
from tensorflow.keras.optimizers import Adam

model.compile(optimizer=Adam(learning_rate=0.001),
              loss='categorical_crossentropy',
              metrics=['accuracy'])
```
12. Create another variables to make it easy to set hyperparameters
```
BATCH_SIZE = 16
IMAGE_SIZE = (224, 224)
```
13. Load image dataset from a directory
```
from tensorflow.keras.utils import image_dataset_from_directory

train = image_dataset_from_directory(
    directory=train_dir,
    label_mode='categorical',
    color_mode='rgb',
    batch_size=BATCH_SIZE,
    image_size=IMAGE_SIZE,
    shuffle=True,
    seed=42)

val = image_dataset_from_directory(
    directory=val_dir,
    label_mode='categorical',
    color_mode='rgb',
    batch_size=BATCH_SIZE,
    image_size=IMAGE_SIZE,
    shuffle=False)
```
14. Train model
```
history = model.fit(
    train,
    validation_data=val,
    epochs=10,
    verbose=2)
```
15. Check training and validation accuracy and training and validation loss using line chart
```
import matplotlib.pyplot as plt

acc = history.history['accuracy']
val_acc = history.history['val_accuracy']
loss = history.history['loss']
val_loss = history.history['val_loss']

epochs_acc = range(len(acc))
epochs_loss = range(len(loss))

fig, (acc_fig, loss_fig) = plt.subplots(1, 2, figsize=(17, 5))

acc_fig.plot(epochs_acc, acc, '#800000', label='Training accuracy')
acc_fig.plot(epochs_acc, val_acc, '#000080', label='Validation accuracy')
acc_fig.set_title('Training and validation accuracy')
acc_fig.legend(["Training", "Validation"], loc ="lower right")

loss_fig.plot(epochs_loss, loss, '#800000', label='Training loss')
loss_fig.plot(epochs_loss, val_loss, '#000080', label='Validation loss')
loss_fig.set_title('Training and validation loss')
loss_fig.legend(["Training", "Validation"], loc ="upper right")

plt.show()
```
16. test model by uploading an image to find out its accuracy
```
import numpy as np
from google.colab import files
from tensorflow.keras.utils import load_img, img_to_array

uploaded = files.upload()

for fn in uploaded.keys():

  path = fn
  img = load_img(path, target_size=(224, 224))
  x = img_to_array(img)
  x = np.expand_dims(x, axis=0)

  images = np.vstack([x])
  classes = model.predict(images, batch_size=8)
  print(fn)

  print('HDPE', '{:.5f}'.format(classes[0, 0]))
  print('LDPE', '{:.5f}'.format(classes[0, 1]))
  print('Others', '{:.5f}'.format(classes[0, 2]))
  print('PET', '{:.5f}'.format(classes[0, 3]))
  print('PP', '{:.5f}'.format(classes[0, 4]))
  print('PS', '{:.5f}'.format(classes[0, 5]))
  print('PVC', '{:.5f}'.format(classes[0, 6]))
```
17. Save model in .h5 format
```
model.save('Recyclass using MobileNetV2.h5')
```

## License

This project is licensed under the terms of the Team C23-PS296 - Capstone Project.
