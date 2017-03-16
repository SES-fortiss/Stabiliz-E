from django.http import HttpResponse
from django.template import RequestContext
from django.shortcuts import render_to_response
from .forms import UserForm, UserProfileForm
from django.contrib.auth import authenticate, login
from django.http import HttpResponseRedirect
from django.contrib.auth.models import User
from django import forms
from fortiss.rpc.client import *

def registration(request):

    context = RequestContext(request)
    queue = 'usermanager-1'
    fortiss_rpc = FortissRpcClient(queue)

    if request.method == 'POST':

        username = request.POST['username']
        password = request.POST['password']
        print("REGISTRATION: Username:  %r  ,  Password:  %r " % (username,password))
    
        response = fortiss_rpc.call("createUser", [username,password])
        print(" User created? ? %r " % (response))

        if response:
                 
            return HttpResponseRedirect('/Success/')
            

        else:
            
            print ("Invalid login details: {0}, {1}".format(username, password))
            return HttpResponse("Sorry, that Username is already taken.")


    else:

        return render_to_response('registration.html', {}, context)


def testlogin(request):

    context = RequestContext(request)
    queue = 'usermanager-1'
    fortiss_rpc = FortissRpcClient(queue)

    if request.method == 'POST':

        username = request.POST['username']
        password = request.POST['password']
        print(" Username:  %r  ,  Password:  %r " % (username,password))
  
        response = fortiss_rpc.call("validLogin", [username,password])
        print(" Valid ? %r " % (response))

        if response:
            
            return HttpResponseRedirect('/Userpage/')

        else:
            
            print ("Invalid login details: {0}, {1}".format(username, password))
            return HttpResponse("Invalid login details supplied.")

    else:

        return render_to_response('loginTEST.html', {}, context)
    
def Login(request):

    context = RequestContext(request)
    queue = 'usermanager-1'
    fortiss_rpc = FortissRpcClient(queue)

    if request.method == 'POST':

        username = request.POST['inputUsername']
        password = request.POST['inputUserpassword']
        print(" Username:  %r  ,  Password:  %r " % (username,password))
  
        response = fortiss_rpc.call("validLogin", [username,password])
        print(" Valid ? %r " % (response))

        if response:
            
            return HttpResponseRedirect('/Userpage/',username)

        else:
            
            print ("Invalid login details: {0}, {1}".format(username, password))
            return HttpResponse("Invalid login details supplied.")

    else:

        return render_to_response('Login.html', {}, context)