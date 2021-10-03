from flask import Flask, redirect, request, render_template, sessions, session, jsonify
from DBConnection import Db
##

import os
from flask import *
import pymysql
from werkzeug.utils import secure_filename

##
from hashlib import sha256
from itertools import izip, count, chain
from struct import pack
from ecdsa.curves import SECP256k1
from ecdsa.util import string_to_number, number_to_string, randrange
from ecdsa.ellipticcurve import Point
import urllib
import base64
from pyDes import des



import hashlib
from flask import send_file
import json

# from shapely.geometry import Point

# from fastecdsa.curve import P256
# from fastecdsa.point import Point




import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText

import base64
import hashlib
Point.__hash__ = lambda self: hash(self.x() + self.y())
Point.__repr__ = Point.__str__
G = SECP256k1.generator




##blockchin




##ovr

##block_clint


from collections import OrderedDict

import binascii

import Crypto
import Crypto.Random
from Crypto.Hash import SHA
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5

import requests
from flask import Flask, jsonify, request, render_template


class Transaction:
    def __init__(self, sender_address, sender_private_key, recipient_address, value):
        self.sender_address = sender_address
        self.sender_private_key = sender_private_key
        self.recipient_address = recipient_address
        self.value = value

    def __getattr__(self, attr):
        return self.data[attr]

    def to_dict(self):
        return OrderedDict({'sender_address': self.sender_address,
                            'recipient_address': self.recipient_address,
                            'value': self.value})

    def sign_transaction(self):
        """
        Sign transaction with private key
        """
        private_key = RSA.importKey(binascii.unhexlify(self.sender_private_key))
        signer = PKCS1_v1_5.new(private_key)
        h = SHA.new(str(self.to_dict()).encode('utf8'))
        return binascii.hexlify(signer.sign(h)).decode('ascii')









##ovr


##
from flask import Flask,render_template,request,session,redirect

from werkzeug.utils import secure_filename
import os
import time

app = Flask(__name__)
app.secret_key="haii"
static_path="D://mea_blockchain_web_and//untitled//static//"
APP_ROOT = os.path.dirname(os.path.abspath(__file__))
UPLOAD_FOLDER = os.path.join(APP_ROOT, 'static/stu')

ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/')
def login():
    return render_template("login index.html")

@app.route('/login_pst',methods=['POST'])
def login1():
    username=request.form['username']
    password=request.form['password']
    qry="select*from login where username='"+username+"'and password='"+password+"'"
    d=Db()
    res=d.selectOne(qry)
    if res is not None:
        type=res['usertype']
        if type=='admin':
            return render_template("admin_home.html")
        else:
            return 'invalid user name or password'
    else:
        return 'invalid user name or password'




@app.route('/adm_home')
def admin_home():
    return render_template("admin_home.html")


@app.route('/adm_add_constitency')
def adm_add_constitency():
    return render_template("add consititency.html")


@app.route('/adm_add_constitency_pst',methods=["POST"])
def adm_add_constitency_pst():
    c=Db()
    Constituency_Name =request.form["textfield"]
    qry="insert into constituency(constituency_name)values('"+Constituency_Name+"')"
    result=c.insert(qry)
    return render_template("add consititency.html")


@app.route('/adm_add_election')
def adm_add_election():
    return render_template("add election.html")

@app.route('/adm_add_election_pst',methods=["POST"])
def adm_add_election_pst():
    c=Db()
    Election_Name =request.form["textfield"]
    Election_Date = request.form["textfield2"]
    Nomination_Date = request.form["textfield3"]
    Result_Date = request.form["textfield4"]
    qry="insert into election_results(election_name,election_date,nomination_date,election_status,result_date)values ('"+Election_Name+"','"+Election_Date+"','"+Nomination_Date+"','pending','"+Result_Date+"')"
    print(qry)
    res=c.insert(qry)
    ############
    if res != None:
        print("ll22")
        qq = "select login_id from user"
        print(qq)
        res22 = c.select(qq)
        print("rs2255=", res22)
        if len(res22) != 0:
            for ik in res22:
                secrets = [random_scalar() for i in range(0, 1)]
                print("s=", secrets)
                sy = str(secrets)
                print("sy=", sy)
                sy1 = sy[1:len(sy) - 1]
                print("sy1=", sy1)

                pubkeys = [G * s for s in secrets]
                # print("pu=", pubkeys)
                qs = "select * from user_sk55 where gid='" + str(res) + "' and uid='" + str(ik['login_id']) + "'"
                rm = c.selectOne(qs)
                # print(type(rm))
                # print("rm=", rm)
                if rm == None:

                    iii3 = "insert into user_sk55 values('" + str(res) + "','" + str(ik['login_id']) + "','" + str(
                        pubkeys) + "','" + sy1 + "','pending')"
                    print(iii3)
                    c.insert(iii3)
                    # mail sending code
                    qq = "select email from user where login_id='" + str(ik['login_id']) + "'"
                    print(qq)
                    rhh = c.selectOne(qq)

                    print("rhh=", rhh['email'])

                    fromaddr = "photolooz.in@gmail.com"
                    toaddr = rhh["email"]
                    msg = MIMEMultipart()
                    msg['From'] = fromaddr
                    msg['To'] = toaddr
                    msg['Subject'] = "groupid with key"

                    body = "groupid=" + str(res) + ",and sk=" + sy1
                    msg.attach(MIMEText(body, 'plain'))

                    server = smtplib.SMTP('smtp.gmail.com', 587)
                    server.starttls()
                    server.login(fromaddr, "sanjidxphotolooz")
                    text = msg.as_string()
                    server.sendmail(fromaddr, toaddr, text)
                    server.quit()

                else:
                    print("noooooooo")

    ########


    return render_template("add election.html")

####algo
#blochin
@app.route('/nn')
def index():


    return render_template('./index.html')


@app.route('/make/transaction')
def make_transaction():
    return render_template('./make_transaction.html')


@app.route('/view/transactions')
def view_transaction():
    return render_template('./view_transactions.html')


@app.route('/wallet/new', methods=['GET'])
def new_wallet():
    random_gen = Crypto.Random.new().read
    private_key = RSA.generate(1024, random_gen)
    public_key = private_key.publickey()
    response = {
        'private_key': binascii.hexlify(private_key.exportKey(format='DER')).decode('ascii'),
        'public_key': binascii.hexlify(public_key.exportKey(format='DER')).decode('ascii')
    }

    return jsonify(response), 200




#algo block ovr

def H(m):
    print("m")
    # print m
    return sha256(m).digest()


def string_to_scalar(s):
    n = string_to_number(s)
    assert 0 <= n < SECP256k1.order
    return n


def random_scalar():
    return randrange(SECP256k1.order)

def bor_H(m, r, i, j):
    print("bor_h 4 values")
    print(m)
    print(r)
    print(i)
    print(j)

    r = serialize_point(r) if isinstance(r, Point) else r
    return string_to_scalar(H(m + r + pack('!ii', i, j)))

def serialize_point(p): # SEC compressed format
    return chr((p.y() & 1) + 2) + number_to_string(p.x(), SECP256k1.order)
def sign(message, rings, secret_keys):

    secret_keys = {G * secret: secret for secret in secret_keys}
    known_pubkeys = secret_keys.keys()
    known_keys_by_ring = [set(known_pubkeys) & set(ring) for ring in rings]
    # check we know a secret key in each ring
    assert all(known_keys_by_ring)
    known_key_indexes = [ring.index(known.pop()) for ring, known in zip(rings, known_keys_by_ring)]
    M = H(message + ''.join(map(serialize_point, chain(*rings))))
    s = [[random_scalar() for _ in ring] for ring in rings]
    k = [random_scalar() for _ in ring]
    e0_hash = sha256()
    for ring, known_key_index, i in izip(rings, known_key_indexes, count()):
        r_i_j = k[i] * G
        for j in range(known_key_index + 1, len(ring)):
            e_i_j = bor_H(M, r_i_j, i, j)
            r_i_j = s[i][j] * G + e_i_j * ring[j]
        e0_hash.update(serialize_point(r_i_j))
    e0_hash.update(M)  # this step not in paper?
    e0 = e0_hash.digest()
    for ring, known_key_index, i in izip(rings, known_key_indexes, count()):
        e_i_j = bor_H(M, e0, i, 0)
        for j in range(0, known_key_index):
            r_i_j = s[i][j] * G + e_i_j * ring[j]
            e_i_j = bor_H(M, r_i_j, i, j + 1)
        secret = secret_keys[ring[known_key_index]]
        s[i][known_key_index] = (k[i] - e_i_j * secret) % SECP256k1.order
    return e0, s
def verify(message1, rings, signature1,signature2):
    print("chk")
    print(message1)
    print(rings)
    print("type of e0")
    print(signature1)
    print(type(signature1))

    print("type of s")
    print(signature2)
    print(type(signature2))


    asm = base64.b64decode(signature1)
    print("asm=", asm)

    ak=str(signature1)
    print(type(ak))

    message=str(message1)
    e0=asm
    #tt = len(signature2)
    # print(tt)
    # sinn = signature2[1:tt - 1]
    # print("sinn=", sinn)
    # ks=sinn.replace("'","")
    # print("ks")
    # print(ks)

    oo=int(signature2)
    s=[]
    u=[]

    u.append(oo)
    s.append(u)
    print(type(message))
    print(type(e0))
    print("ssss")
    print(type(s))

    print(s)
    print("mss")
    print(type(message))
    print(type(rings))

    #e0, s = signature
    M = H(message + ''.join(map(serialize_point, chain(*rings))))
    print("m=",str(M))
    e0_hash = sha256()
    print("e0hash=",str(e0_hash))
    for i,ring in enumerate(rings):
        print("cmmm")
        # print(M)
        # print (e0)
        # print(i)

        e_i_j = bor_H(M, e0, i, 0)
        for j,pubkey in enumerate(ring):
            print("s[i][j]=", s[i][j])

            print(str(i))
            print(str(j))
            print("G=",G)
            r = s[i][j] * G + pubkey * e_i_j
            print("r=",r)
            e_i_j = bor_H(M, r, i, j+1)
        e0_hash.update(serialize_point(r))
    e0_hash.update(M)
    return e0_hash.digest() == e0


def verify1(message, rings, signature):
    e0, s = signature

    print("signatue e0")
    print(type(e0))
    print(type(s))
    print(type(message))
    print(type(rings))
    print(e0)
    print(s)
    M = H(message + ''.join(map(serialize_point, chain(*rings))))
    print("m=",M)
    e0_hash = sha256()
    print(type(rings))
    for i,ring in enumerate(rings):
        print("cmmm")
        # print(M)
        # print (e0)
        # print(i)

        e_i_j = bor_H(M, e0, i, 0)
        for j,pubkey in enumerate(ring):
            print("s[i][j]=", s[i][j])

            print(str(i))
            print(str(j))
            print("G=",G)
            r = s[i][j] * G + pubkey * e_i_j
            print("r=",r)
            e_i_j = bor_H(M, r, i, j+1)
        e0_hash.update(serialize_point(r))
    e0_hash.update(M)
    return e0_hash.digest() == e0


@app.route("/very_scrt")
def min_viws():

    return render_template("very_scrt.html")


@app.route("/very_scrt_pst",methods=['POST'])
def min_vijws_pst():
    print("mm")
    c = conn()

    sc = request.form["textfield"]
    reg_id = str(session["lid"])
    gid=str(session["pid"])


    a = []
    ae = []
    a.append(long(sc))
    pubkeys = [G * s for s in a]
    rings = [pubkeys[:2]]
    db=Db()

    ss22 = "select * from user_sk55 where type='pending' and uid='" + str(reg_id) + "' and gid='"+str(gid)+"'"
    print(ss22)
    res = db.selectOne(ss22)

    print("rr=",res)
    if res is not None:
        print("yes")
        uu="update user_sk55 set type='ok' where gid='"+str(gid)+"' and uid='"+str(reg_id)+"'"
        print(uu)
        db.update(uu)
        return redirect("/nn")
    else:
        return "no"



###





@app.route('/generate/transaction', methods=['POST'])
def generate_transaction():
    sender_address = request.form['sender_address']
    sender_private_key = request.form['sender_private_key']
    # recipient_address = request.form['recipient_address']
    recipient_address =sender_address
    value = request.form['amount']

    transaction = Transaction(sender_address, sender_private_key, recipient_address, value)

    response = {'transaction': transaction.to_dict(), 'signature': transaction.sign_transaction()}
    print("hhhh")
    print(transaction.sign_transaction())

    print("mmm")
    print(response)
    c=conn()
    qq="insert into votng(can_id,uid)values(2,2,2)"
    print(qq)
    c.nonreturn(qq)
    print("ooooooooooooo")
    # return render_template("adminhome.html")

    return jsonify(response), 200




#finsh algo def




@app.route('/adm_candidate_mgmt')
def adm_candidate_mgmt():
    return render_template("candidate mngmnt.html")

@app.route('/adm_comp_rply/<cid>')
def adm_comp_rply(cid):
    session['cid']=cid
    return render_template("comp reply.html")

@app.route('/adm_comp_rply_pst',methods=["POST"])
def adm_comp_rply_pst():

    reply = request.form["textarea"]
    c = Db()
    qry = "Update complaint set reply='" + reply + "' where complaint_id='" +str(session['cid']) +"'"
    print(qry)
    result = c.update(qry)
    return render_template("admin_home.html")

@app.route('/adm_complint_mgmt')
def adm_complint_mgmt():
    c = Db()
    qry ="select complaint.*,user.name,user.place,user.phone_number,user.login_id from complaint,user where complaint.user_id=user.login_id and complaint.type='user'"
    result = c.select(qry)
    return render_template("complint mngmnt.html", data=result)

@app.route('/adm_complint_mgmt2')
def adm_complint_mgmt2():
    c = Db()
    qry ="select complaint.*,user.name,user.place,user.phone_number,user.login_id from complaint,user,candidate where complaint.user_id=candidate.login_id and candidate.uid=user.user_id and complaint.type='candi'"
    print(qry)
    result = c.select(qry)
    print(result)
    return render_template("cmp.html", data=result)


@app.route('/adm_consttcy_mgmt')
def adm_consttcy_mgmt():
    return render_template("consttcy mngmnt.html")

@app.route('/adm_login')
def adm_login():
    return render_template("login.html")

@app.route('/adm_login_pst',methods=["POST"])
def adm_login_pst():
    Username =request.form["textfield"]
    Password = request.form["textfield2"]
    return render_template("login.html")


@app.route('/adm_update_constitency')
def adm_update_constitency():
    return render_template("update constitecy.html")

@app.route('/adm_update_constitency_pst',methods=["POST"])
def adm_update_constitency_pst():
    x=session['cid']
    c=Db()
    Constituency_Name=request.form["textfield"]
    qry="update constituency set constituency_name='"+Constituency_Name+"'where constituent_id='"+str(x)+"'"
    res=c.update(qry)
    return render_template("admin_home.html")

@app.route('/adm_user_verify')
def adm_user_verify():
    return render_template("user verify.html")

@app.route('/adm_view_candidate')
def adm_view_candidate():
    c=Db()
    qry="select user.name as candidatename,nomination.* ,constituency.constituency_name from nomination,candidate,user,election_results,constituency where candidate.login_id=user.login_id and candidate.candidate_id=nomination.candidate_id and constituency.constituent_id=nomination.constituent_id and nomination.election_id=election_results.election_id"
    result=c.select(qry)
    return render_template("view candidate.html",data=result)

@app.route('/adm_view_consttncy')
def adm_view_consttncy():
    c=Db()
    qry="select nomination_id,constituency.constituency_name,election_results.election_name,party,user.name,user.photo,nomination.symbol from nomination,user,candidate,constituency,election_results where nomination.election_id=election_results.election_id and nomination.candidate_id=candidate.login_id and candidate.uid=user.user_id and nomination.constituent_id=constituency.constituent_id and nomination.status='pending'"
    res=c.select(qry)
    print(qry)
    qry2 = "select * from constituency"
    res2 = c.select(qry2)
    return render_template("view costtncy.html",data=res,data2=res2)

@app.route('/adm_view_consttncypost',methods=['post'])
def adm_view_consttncypost():
    const = request.form['select']
    c=Db()
    qry2="select * from constituency"
    res2=c.select(qry2)
    qry= "select nomination_id,constituency.constituency_name,election_results.election_name,party,user.name,user.photo from nomination,user,candidate,constituency,election_results where nomination.election_id=election_results.election_id and nomination.candidate_id=candidate.login_id and candidate.uid=user.user_id and nomination.constituent_id=constituency.constituent_id and nomination.status='pending' and constituency.constituent_id='"+str(const)+"'"
    print(qry)
    res=c.select(qry)
    print(res)
    return render_template("view costtncy.html",data=res,data2=res2)

@app.route('/adm_view_consttncy_approve/<id>')
def adm_view_consttncy_approve(id):
    c=Db()
    qry="update nomination set status='approve' where nomination_id='"+str(id)+"'"
    res=c.update(qry)
    print(qry)

    return redirect("/adm_view_consttncy")

@app.route('/adm_view_consttncy_rej/<id>')
def adm_view_consttncy_rej(id):
    c=Db()
    qry="update nomination set status='reject' where nomination_id='"+str(id)+"'"
    res=c.update(qry)

    return redirect("/adm_view_consttncy")



@app.route('/adm_view_election')
def adm_view_election():
    c=Db()
    qry="select * from election_results"
    result = c.select(qry)
    return render_template("view election.html",data=result)

@app.route('/adm_view_election_res_pub/<id>/<id2>')
def adm_view_electiggon(id,id2):
    c=Db()
    mri="select * from election_results where election_status='pending' and election_id='"+str(id)+"'"
    das=c.selectOne(mri)
    if das!=None:
        print("yyyy22")
        print("hai")
        import datetime
        aa=datetime.datetime.today()
        print(aa)
        a1=str(aa).split(" ")
        b1=str(id2)

        print("a1=",a1[0])
        print("id2=",id2)
        print("b1=",b1)
        ar=[]
        if a1[0]==b1:
            c=Db()
            qq="select nomination_id from nomination where election_id='"+str(id)+"' and status='approve'"
            res22=c.select(qq)
            for ii in res22:
                ar.append(ii['nomination_id'])
            for r in ar:
                print("")
                qq = "select count(vote_id) as ccc from vote,nomination where nomination.nomination_id=vote.nomination_id and nomination.election_id='" + str(id) + "' and vote.nomination_id='" + str(r) + "'"
                print(qq)
                yy = c.selectOne(qq)
                print(yy)

                q3 = "insert into result(nid,election_id,vote_cnt)values('" + str(r) + "','"+str(id)+"','" + str(yy['ccc']) + "')"
                print(q3)
                c.insert(q3)
            q55 = "update election_results set election_status='ok' where election_id='" + str(id) + "'"
            c.update(q55)

            q44 = "select max(vote_cnt)as vv from result,nomination where nomination.nomination_id=result.nid and nomination.election_id='"+str(id)+"'"
            ms = c.selectOne(q44)
            print(ms)
            q66="select count(result_id) as cc from result where vote_cnt='" + str(ms['vv']) + "' and election_id='"+str(id)+"'"
            rms=c.selectOne(q66)
            print("cn=",rms['cc'])
            if int(rms['cc'])==1:

                q5 = "update result set status='win' where vote_cnt='" + str(ms['vv']) + "'"
                mk = c.update(q5)
            else:
                q5 = "update result set status='reelection' where vote_cnt='" + str(ms['vv']) + "'"
                mk = c.update(q5)

            print("yyy")
            return render_template("admin_home.html")
        else:
            print("nnnn")
            return "no"
    else:
        return "no"


@app.route('/adm_view_user')
def adm_view_user():
    c=Db()
    qry="select * from user"
    result = c.select(qry)
    return render_template("view user..html",data=result)

@app.route('/adm_constituency_management')
def adm_consituency_management():
    c=Db()
    qry="select*from constituency"
    result = c.select(qry)
    return render_template("constituency management.html",data=result)

@app.route('/adm_delete_constitency/<id>')
def adm_delete_constitency(id):
    qry="delete from constituency where constituent_id='"+id+"' "
    c=Db()
    c.delete(qry)
    return adm_consituency_management()

@app.route('/adm_edit_constitency/<id>')
def adm_edit_constitency(id):
    d=Db()
    qry="select*from consituency where constituency_id='"+"'"
    res=d.selectOne(qry)
    return render_template("update constitecy.html",data=res)

@app.route('/view_user_more/<id>')
def view_user_more(id):
    qry=" select*from user where login_id='"+id+"' "
    c=Db()
    res=c.selectOne(qry)
    session['fd']=id
    return render_template("user verify.html",data=res)

@app.route('/verify_approve_reject',methods=['post'])
def verify_approve_reject():
    d=Db()
    x=session['fd']
    btn=request.form['button']
    if btn=="Approve":
        qry="update login set usertype='user'where lid='"+str(x)+"'"
        res=d.update(qry)
        qry2 = "update user set status='approve' where login_id='" + str(x) + "'"
        res = d.update(qry2)

    else:
        qry = "update login set usertype='reject'where lid='" + str(x) + "'"
        res = d.update(qry)
        qry2 = "update user set status='reject' where login_id='" + str(x) + "'"
        res = d.update(qry2)

    return render_template("admin_home.html")

@app.route('/adm_delete_election/<id>')
def adm_delete_election(id):
    qry="delete from election_results where election_id='"+id+"' "
    c=Db()
    c.delete(qry)
    return adm_view_election()

@app.route('/adm_edit_constituency/<id>')
def adm_edit_constituency(id):
    session['cid']=id
    qry="select*from constituency where constituent_id='"+id+"'"
    c=Db()
    res=c.selectOne(qry)
    return render_template("update constitecy.html",data=res)

@app.route('/adm_edit_election/<id>')
def adm_edit_election(id):
    session['cid']=id
    qry="select*from election_results where election_id='"+id+"'"
    c=Db()
    res=c.selectOne(qry)
    return render_template("edit election.html",data=res)

@app.route('/adm_edit_election_pst',methods=["POST"])
def adm_edit_election_pst():
    x=session['cid']
    c=Db()
    Election_Name=request.form["textfield"]
    Election_Date=request.form["textfield2"]
    Nomination_Date = request.form["textfield3"]
    Result_Date = request.form["textfield4"]
    qry="update election_results set election_name='"+Election_Name+"',election_date='"+Election_Date+"',nomination_date='"+Nomination_Date+"',result_date='"+Result_Date+"'where election_id='"+str(x)+"'"
    res=c.update(qry)
    return render_template("admin_home.html")


@app.route('/adm_login_index')
def adm_login_index():
    return render_template("login index.html")

@app.route('/adm_home_index')
def adm_home_index():
    return render_template("home index.html")



#**********************************************Android_****************************************************************


@app.route('/and_login',methods=['post'])
def and_user_login():
    print("qqq22")
    username = request.form["uname"]
    password = request.form["pwd"]
    qry="select*from login where username='"+username+"'and password='"+password+"'"
    print(qry)
    d=Db()
    res=d.selectOne(qry)
    print(res)
    if res is not None:
        if res['usertype']=="user":
            print("hhh")
            qry22="select photo,user_id,con_id from user where login_id='"+str(res['lid'])+"'"
            msk=d.selectOne(qry22)
            print(msk)
        
    
            return jsonify(status='ok',lid=res['lid'],type=res['usertype'],pho=msk['photo'],uid=msk['user_id'],con_id=msk['con_id'])
        elif res['usertype']=="candi":
            return jsonify(status='ok',lid=res['lid'],type=res['usertype'],pho="",uid="",con_id="")
        else:
            return jsonify(status='no')        
        
        
            
    else:
        return jsonify(status='no')

@app.route('/and_view_consi',methods=['post'])
def and_view_consi():
    c = Db()
    print("hai")
   
    qry="select constituent_id,constituency_name from constituency"
    print(qry)
    res = c.select(qry)
    print(res)
    return jsonify(status="ok", data=res)


@app.route('/usr_reg',methods=['post'])
def and_user_registration():
    print("hhh")
    name = request.form["name"]
    gender = request.form["gen"]
    dob = request.form["dob"]
    phone = request.form["phone"]
    email = request.form["email"]

    house_no = request.form["hn"]
    house_name = request.form["hs"]
    place = request.form["place"]
    post = request.form["post"]
    pin = request.form["pin"]
    district = request.form["dist"]
    state = request.form["state"]
    voter_id = request.form["voter_id"]
    img = request.form["pho"]
    con_id=request.form["con_id"]
    pwd=request.form["pwd"]
    print(img)
    import base64
    import datetime
    a = base64.b64decode(img)
    dt = datetime.datetime.now()
    dd = str(dt).replace(" ", "_").replace(":", "_").replace("-", "_")
    fh = open(static_path+"user\\" + dd + ".jpg", "wb")
    path = "/static/user/" + dd + ".jpg"
    print(path)
    fh.write(a)
    fh.close()
    c=Db()
    qry1="INSERT INTO login (username,password,usertype) values('"+email+"','"+pwd+"','userr')"
    res=c.insert(qry1)
    qry="INSERT INTO user (login_id,voter_id,name,gender,dob,phone_number,email,photo,house_no,house_name,place,post,pin,district,state,con_id) values('"+str(res)+"','"+voter_id+"','"+name+"','"+gender+"','"+dob+"','"+phone+"','"+email+"','"+path+"','"+house_no+"','"+house_name+"','"+place+"','"+post+"','"+pin+"','"+district+"','"+state+"','"+str(con_id)+"')"
    res1=c.insert(qry)
    ###qr
    print("qr1")
    import qrcode
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(res)
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white")
    print(type(img))
    im1 = img.save("D://mea_blockchain_web_and//untitled//static//sc.jpg")
    print("qr fin")

    ###mail sending
    from email.MIMEMultipart import MIMEMultipart
    from email.MIMEText import MIMEText
    from email.MIMEImage import MIMEImage
    import smtplib
    import smtplib
    from email.mime.multipart import MIMEMultipart
    from email.mime.text import MIMEText
    from email.mime.base import MIMEBase
    from email import encoders

    fromaddr = "photolooz.in@gmail.com"
    toaddr = email
    msg = MIMEMultipart()
    msg['From'] = fromaddr
    msg['To'] = toaddr
    msg['Subject'] = "qr"

    filename = "a1.jpg"
    attachment = open("D://mea_blockchain_web_and//untitled//static//sc.jpg", "rb")

    part = MIMEBase('application', 'octet-stream')
    part.set_payload((attachment).read())
    encoders.encode_base64(part)
    part.add_header('Content-Disposition', "attachment; filename= %s" % filename)
    msg.attach(part)

    body = "qr"
    msg.attach(MIMEText(body, 'plain'))

    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(fromaddr, "sanjidxphotolooz")
    text = msg.as_string()
    server.sendmail(fromaddr, toaddr, text)
    server.quit()
    print("fin")

    ##ovr


    return jsonify(status='ok')


@app.route('/and_send_complaint',methods=['post'])
def and_send_complaint():
    user_id = request.form["lid"]
    complaint = request.form["cmp"]
    ty = request.form["ty"]
    
    c = Db()
    qry = "INSERT INTO complaint (user_id,complaint_date,reply,complaint,type) values('"+user_id+"',curdate(),'pending','"+complaint+"','"+ty+"')"
    res = c.insert(qry)
    return jsonify(status="ok")


@app.route('/req_candi',methods=['post'])
def req_candi():
    print("hai")
    user_id = request.form["lid"]
    uid = request.form["uid"]
    em = request.form["em"]
    
   
    
    c = Db()
    qq="select * from login where username='"+em+"' and usertype='candi'"
    print(qq)
    rk=c.selectOne(qq)
    print(rk)
    if rk is None:
        print("jjj")
    
        qry = "insert into login(username,password,usertype)values('"+em+"','12345','candi')"
        res = c.insert(qry)
        qry2 = "insert into candidate(login_id,date,uid)values('"+str(res)+"',curdate(),'"+str(uid)+"')"
        res2 = c.insert(qry2)
        return jsonify(status="ok")
    else:
        print("jjj")
        return jsonify(status="no")
   



@app.route('/and_view_reply',methods=['post'])
def and_view_reply():
    c = Db()
    lid = request.form["lid"]
    qry="SELECT complaint,complaint_date,reply FROM complaint WHERE user_id='"+lid+"'"
    res = c.select(qry)
    return jsonify(status="ok", data=res)


@app.route('/and_constiuency_view',methods=['post'])
def and_constiuency_view():
    qry="SELECT constituent_id,constituency_name FROM constituency"
    c=Db()
    res=c.select(qry)
    return jsonify(status="ok",data=res)

@app.route('/and_view_result',methods=['post'])
def and_view_result():
    qry="select election_results.election_name,constituency.constituency_name,user.name,nomination.party,nomination.symbol,result.vote_cnt as cnt33 from candidate,nomination,user,result,election_results,constituency where constituency.constituent_id=nomination.constituent_id and result.election_id=election_results.election_id and nomination.candidate_id=candidate.login_id and candidate.uid=user.user_id and nomination.nomination_id=result.nid and result.status='win'"
    c=Db()
    res=c.select(qry)
    print(res)
    return jsonify(status="ok",data=res)

@app.route('/and_nomin_add',methods=['post'])
def and_nomin_add():
    cid=request.form["cid"]
    consi_id=request.form["consi"]
    eid=request.form["eid"]
    pt=request.form["pt"]
    pic = request.form['pic']
    import base64

    a = base64.b64decode(pic)
    import datetime
    now = datetime.datetime.now()

    md = str(now.hour) + "_" + str(now.minute) + "_" + str(now.second)
    abc = "sy/" + md + ".jpg"
    fh = open(static_path + "sy/" + md + ".jpg", "wb")

    fh.write(a)
    fh.close()

    path = "sy/" + md + ".jpg"

    qry="insert into nomination(candidate_id,constituent_id,election_id,party,symbol,status)values('"+str(cid)+"','"+str(consi_id)+"','"+str(eid)+"','"+pt+"','"+path+"','pending')"
    c=Db()
    res=c.insert(qry)
    return jsonify(status="ok")

@app.route('/and_nomination_view',methods=['post'])
def and_nomination_view():
    cid=request.form["lid"]
    qry="select nomination_id,election_results.election_name,party,status from nomination,election_results where election_results.election_id=nomination.election_id and nomination.candidate_id='"+cid+"'and nomination.status='approve'"
    c=Db()
    res=c.select(qry)
    return jsonify(status="ok",data=res)

@app.route('/and_nomination_view_pending',methods=['post'])
def and_nomination_view_pending():
    cid=request.form["lid"]
    qry="select nomination_id,election_results.election_name,party,status from nomination,election_results where election_results.election_id=nomination.election_id and nomination.status='pending' and nomination.candidate_id='"+cid+"'"
    c=Db()
    res=c.select(qry)
    return jsonify(status="ok",data=res)
@app.route('/and_nomination_del',methods=['post'])
def and_nomination_view_del():
    cid=request.form["nid"]
    qry="delete from nomination where nomination_id='"+cid+"'"
    c=Db()
    res=c.insert(qry)
    return jsonify(status="ok")

@app.route('/and_election_view',methods=['post'])
def and_election_veiw():
    qry="select election_id,election_name,election_date,nomination_date,result_date from election_results"
    c=Db()
    res=c.select(qry)
    return jsonify(status="ok",data=res)


@app.route('/and_election_view2',methods=['post'])
def and_election_veiw2():
    print("hai")
    cid=request.form["cid"]
    qry="select nomination.nomination_id,nomination.candidate_id,party,symbol,user.name from user,nomination,election_results,candidate where user.user_id=candidate.uid and candidate.login_id=nomination.candidate_id and nomination.election_id=election_results.election_id and user.con_id=nomination.constituent_id and nomination.status='approve' and election_results.election_date=curdate() and nomination.constituent_id='"+str(cid)+"'"
    print(qry)
    c=Db()
    res=c.select(qry)
    print(res)
    return jsonify(status="ok",data=res)

@app.route('/email_veri',methods=['post'])
def email_veri():
    em=request.form["em"]
    import random
    rnd=random.randint(0000,9999)
    print("rrrrrrrrr=",rnd)

    # # mail sending code
    #
    fromaddr = "photolooz.in@gmail.com"
    toaddr = em
    msg = MIMEMultipart()
    msg['From'] = fromaddr
    msg['To'] = toaddr
    msg['Subject'] = "OTP"

    body = "YOUR OTP="+str(rnd)
    msg.attach(MIMEText(body, 'plain'))

    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(fromaddr, "sanjidxphotolooz")
    text = msg.as_string()
    server.sendmail(fromaddr, toaddr, text)
    server.quit()

    return jsonify(status="ok",rnd=str(rnd))



@app.route('/view_qr',methods=['post'])
def qr_check():
    print("hai")
    cid=request.form["oid"]
    print(cid)
    lid=request.form["lid"]
    print(lid)
    if cid==lid:
        print("ok")
        return jsonify(status="ok")
    else:
        print("no")
        return jsonify(status="no")



# @app.route('/care_emotion_add',methods=['post'])
# def care_emotion_add():
#     print("hlw")
#
#     pho=request.form['photo22']
#     print("pho=",pho)
#
#     file=request.form['pho']
#     student_id = request.form['lid']
#     print("QQQ")
#
#
#
#     import base64
#     a=base64.b64decode(file)
#     fh = open(static_path+"a1.jpg", "wb")
#     fh.write(a)
#     fh.close()
#
#     ##face rec
#
#     photo = static_path+"a1.jpg"
#     print("jjjjjjjjjjjjjj")
#
#     import base64
#
#     import face_recognition
#
#     img = static_path+pho
#     print("img2=", img)
#
#     print("path=====22")
#     print(img)
#     known_faces = []
#     b_img = face_recognition.load_image_file(img)
#     b_imgs = face_recognition.face_encodings(b_img)[0]
#     known_faces.append(b_imgs)
#
#     unknown_image = face_recognition.load_image_file(photo)
#     b_img = face_recognition.load_image_file(img)
#     m = len(face_recognition.face_encodings(unknown_image))
#     print("printing results")
#     for a in range(m):
#         s = face_recognition.face_encodings(unknown_image)[a]
#         unknown_encoding = face_recognition.face_encodings(unknown_image)[a]
#         results = face_recognition.compare_faces(known_faces, unknown_encoding, tolerance=0.45)
#         print(str(results))
#
#         if str(results) == "[True]":
#             print("fc rec")
#             db = Db()
#
#             #qry = "select login_id,name,photo from students where login_id='" + str(student_id) + "'"
#             #print(qry)
#             #res = db.selectOne(qry)
#             #db=Db()
#             #qq="select photo from user where login_id='"+str(lid)+"'"
#             #rkk=db.selectOne(qq)
#
#
#             if res is None:
#                 print("jjj")
#                 return jsonify(status="no")
#             else:
#                 return jsonify(status="ok")
#
#         else:
#             print("no fc")
#             return jsonify(status="no")




@app.route("/very_scrt_pstt",methods=['POST'])
def min_vijws_pstt():
    print("mm")
    c = Db()

    sc = request.form["textfield"]
    reg_id = request.form["lid"]
    gid=request.form["eid"]


    a = []
    ae = []
    a.append(long(sc))
    pubkeys = [G * s for s in a]
    rings = [pubkeys[:2]]
    db=Db()

    ss22 = "select * from user_sk55 where type='pending' and uid='" + str(reg_id) + "' and gid='"+str(gid)+"'"
    print(ss22)
    res = db.selectOne(ss22)

    print("rr=",res)
    if res is not None:
        print("yes")
        uu="update user_sk55 set type='ok' where gid='"+str(gid)+"' and uid='"+str(reg_id)+"'"
        print(uu)
        db.update(uu)
        return jsonify(status="ok")
    else:
        return jsonify(status="no")


@app.route('/wallet_new22', methods=['POST'])
def new_wallet22():
    print("jjjj")
    uid=request.form["lid"]
    nid=request.form["nid"]
    lati = request.form["lati"]
    longi = request.form["longi"]

    random_gen = Crypto.Random.new().read
    private_key = RSA.generate(1024, random_gen)
    public_key = private_key.publickey()
    # response = {
    #     'private_key': binascii.hexlify(private_key.exportKey(format='DER')).decode('ascii'),
    #     'public_key': binascii.hexlify(public_key.exportKey(format='DER')).decode('ascii')
    # }
    priv=binascii.hexlify(private_key.exportKey(format='DER')).decode('ascii')
    pubb=binascii.hexlify(public_key.exportKey(format='DER')).decode('ascii')
    print("prive...................")
    print(priv)
    print("fiiiiiiiiiiiiiiiiii")
    print(pubb)
    print("ovvvvvvvvvvvvvvv")

    sender_address = pubb
    sender_private_key = priv
    recipient_address = sender_address
    value = 1
    print("yessss")

    transaction = Transaction(sender_address, sender_private_key, recipient_address, value)

    response = {'transaction': transaction.to_dict(), 'signature': transaction.sign_transaction()}
    print("hhhh")
    print(transaction.sign_transaction())

    print("mmm")
    print(response)
    c=Db()
    qq="insert into vote(nomination_id,voter_id,lati,longi)values('"+str(nid)+"','"+str(uid)+"','"+str(lati)+"','"+str(longi)+"')"
    print(qq)
    c.update(qq)
    print("ooooooooooooo")

    return jsonify(status='ok')

if __name__ == '__main__':
    from argparse import ArgumentParser

    parser = ArgumentParser()
    parser.add_argument('-p', '--port', default=8080, type=int, help='port to listen on')
    # parser.add_argument('-p', '--port', default=5000, type=int, help='port to listen on')

    args = parser.parse_args()
    port = args.port

    # app.run(host='127.0.0.1', port=port,debug=True)
    app.run(host='0.0.0.0', port=port, debug=True)


