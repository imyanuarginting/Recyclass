# Recyclass Application - overview
Recyclass (Recycle Classification) is a computer vision based waste classification application. This application use machine learning to recognize the type of plastic based on the image that is uploaded. There are 7 types of plastic that we can organized, which are: HDPE, LDPE, PET, PP, PS, PVC, and Others. We also have related articles feature to increase recycling knowledge, as well as a nearby waste bank feature to find the nearest waste bank location. The aim of this application is to increase awareness and participation in recycling waste especially plastic.

# Mobile Development Workflow
1. Design UI/UX
   
There are 2 things that we did at this stage which are making the design concept and prototyping. When creating the design concept, we determine the design type and color palette by considering functionality and target users. After that, in the Figma software, we started making low-fidelity and high-fidelity designs and then continued with making prototypes. Here is the final UI/UX design (expected):

![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/25a64687-0a16-45d4-8c7b-7de05d5221ec)



And here's the UI/UX design that we can implement in the application (reality):

![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/881cf8ef-7de7-4032-9c11-39abb38df747)


Figma link: https://www.figma.com/file/Pduoq016qTjXnREzluH8G1/Recyclass-Apps?type=design&node-id=4%3A10&t=qslsdA8WzfLKSEZj-1

We also planned to make some animations to make the design more dynamic and not seem very stiff.


2. Design Implementation
   
At this stage, we start dividing the work by the features and the flow of the application. Each of us will implement the design into the application using the XML language. We chose XML because we feel more fluent in using it than other possible languages, we also think that the components for the UI/UX of this application are still relatively simple so they are still quite easy to implement using XML. Here are some sample code and implementation results.

![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/abfcfaee-f6c3-4714-b6a3-fb8ac31b5118)
![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/103caf23-328c-4add-8746-187d92cd61bf)

To implement the animation, we use the motion layout.

3. Testing API
   
Whenever we get a new endpoint, we start to work on it from this stage. The first thing that we do was try the endpoint via Postman. After we were sure that it runs properly, we started to try to get the data on the application using the retrofit library, Gson converter, and okhttp3. We use Retrofit and Gson converter to ease the process of pulling the data from the network and to make it easier to convert JSON data received from endpoints into the data form that we can process in applications. We use the Okhhtp3 library to help us to see the endpoint calling process and the response that is given.
![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/6e9d39f5-e0bb-40f5-9fc1-ce203010a455)
![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/a21b3d0a-c7ce-4211-8a2b-ae0a1a65ab64)
![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/3819e914-a21e-4863-b7f9-118c7dd48e7e)

4. Bussiness Logic Implementation
   
 - Feature: Image Detection, Related Article
   
   ![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/7dd72a92-665e-482a-aa9b-3509d8ca45f2)
   ![image](https://github.com/imyanuarginting/Recyclass/assets/128117129/6858e458-30a7-43ec-9301-409edf5a9651)

   Work Explanation:
   
   This is the working process of the main feature of this application. One of the things that can be done at the start of this stage is to make the          camera things working to take a picture and make sure that the application can get the result and then display it. This application utilizes the camera    application of the user's device (Intent camera). After the image has been successfully retrieved and stored as a temporary file, the image will be        sent to the server via the API with Multipart.
   
   If the image upload is successful, the system will automatically call the next endpoint to get the type of plastic obtained from the classification        results. If the classification results have been obtained, a condition check will be carried out to assess whether the image contains plastic that is      recognized as one of the types of plastic.
   
   The next thing that can be done is to make the next page (we’ll call it ”List Article Page”) that displays the type of plastic and also a list of       
   articles. The type of plastic is obtained from the classification results on the previous page. On this page, the endpoint called is the one used to       get the list of articles. This endpoint requires a plastic-type parameter to obtain the appropriate data. To display the list of articles, we use the      paging library. This is done to prepare the application to display even more article data.
   
   And finally, we continued with making the application able to take pictures from the gallery, adjust page turns, pay attention to other                 
   details, find bugs, and then fix them.

- Feature: Maps
  
  Not yet

5. Deployment
   
Generating the APK file from the project.

# APK File

https://www.dropbox.com/s/l2dldt12x89z7v5/app-debug.apk?dl=0
