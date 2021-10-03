from flask import Flask, render_template, request

import base64

import datetime
from flask.globals import session
from flask.json import jsonify
from DBConnection import Db




app = Flask(__name__)

static_path="D://mea_blockchain_web_and//untitled//"

@app.route('/care_emotion_add',methods=['post'])
def care_emotion_add():
    print("hlw")

    pho=request.form['photo22']
    print("pho=",pho)

    file=request.form['pho']
    student_id = request.form['lid']
    print("QQQ")
    
   

    import base64
    a=base64.b64decode(file)
    fh = open(static_path+"a1.jpg", "wb")
    fh.write(a)
    fh.close()

    ##face rec

    photo = static_path+"a1.jpg"
    print("jjjjjjjjjjjjjj")

    import base64
    
    import face_recognition

    img = static_path+pho
    print("img2=", img)
  
    print("path=====22")
    print(img)
    known_faces = []
    b_img = face_recognition.load_image_file(img)
    b_imgs = face_recognition.face_encodings(b_img)[0]
    known_faces.append(b_imgs)

    unknown_image = face_recognition.load_image_file(photo)
    b_img = face_recognition.load_image_file(img)
    m = len(face_recognition.face_encodings(unknown_image))
    print("printing results")
    for a in range(m):
        s = face_recognition.face_encodings(unknown_image)[a]
        unknown_encoding = face_recognition.face_encodings(unknown_image)[a]
        results = face_recognition.compare_faces(known_faces, unknown_encoding, tolerance=0.45)
        print(str(results))

        if str(results) == "[True]":
            print("fc rec")
            return jsonify(status="ok")

         

        if str(results) == "[False]":




           
            return jsonify(status="no")



if __name__ == '__main__':
    app.run(debug=True,port=5000,host='0.0.0.0')

############################################

