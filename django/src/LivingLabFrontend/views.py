from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.template import RequestContext
from django.template import Template, Context
from django.shortcuts import render
from django.shortcuts import render_to_response
from TemplateFolder.models import posts
from .forms import RegistrationForm
from .forms import LoginForm
from fortiss.rpc.client import *
from django.core.urlresolvers import reverse
import datetime
from decimal import Decimal, getcontext

#----------------------------------------------------------------------------------------------------------------------------------------


getcontext().prec = 4

#Area for testing new implementations:
def start(request):
    
    	
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT devid, value FROM DoubleEvents WHERE timestamp = 1425913244825 and value = 100 order by timestamp desc LIMIT 0 , 20"])

    fortiss_rpc.destroy()
    
    
    template = "start.html"


    if response != None:

  
        context = {'name': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"


def testarea(request):
    template = "testarea.html"
    return render(request, template, {})

#---------------------------------------------------------------------------------------------------------------------------------------


#---------------------------------------------------------------------------------------------------------------------------------------

# All relevant views:

def WelcomePage(request):
    
    context = {}
    template = "WelcomePage.html"
    return render(request, template, context)


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



def Userpage(request):

    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "Userpage.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"

    

def fortiss(request):

    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "fortiss.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"
    


def Devices(request):

    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "Devices.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"


def Settings(request):

    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "Settings.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"



def Room(request):
    
    currentURL = request.path
    print (request.path)
    array = currentURL.split("/")
    target = array[(len(array)-2)]
    print (target)
    
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    rooms = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])
    room = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerHRName = '%s' LIMIT 0,30" % target])
    roomID = fortiss_rpc.call("getSQLResults", ["SELECT ContainerID FROM ContainerManager_Containers WHERE ContainerHRName = '%s' LIMIT 0,30" % target])

    fortiss_rpc.destroy()
    
    queue = 'containermanager-query-1'
    fortiss_rpc = FortissRpcClient(queue)
    
    sensorhrnamelist = [[]]


   
    for entrylist in roomID:
        print(entrylist, len(entrylist))
        tobedestroyed = FortissRpcClient(queue)

        for entry in entrylist.items():
            print(entry[1])
            sensors = tobedestroyed.call("getSensorsInContainer",["%s" % entry[1] ])
            tobedestroyed2 = FortissRpcClient(queue)
            print("LIST OF SENSORS RECEIVED FROM call.getSensorsInContainer")
            print(sensors)
            
            print(sensors.items())
            for sensortuplelist in sensors.items():
                sensorvalue = tobedestroyed2.call("getMeanByType",["%s" % entry[1], sensortuplelist[1]])
                if not isinstance(sensorvalue, str):
                    sensorvalue = round(sensorvalue,2)
                temp = []
                temp.append(sensortuplelist[1])
                temp.append(sensorvalue)
                sensorhrnamelist.append(temp)
                print("Represents final sensor list send to ROOM.html")
                print(sensorhrnamelist)
            
            tobedestroyed2.destroy()    
        tobedestroyed.destroy()    
    
    
    fortiss_rpc.call("getSensorsInContainer",["%s" % entry[1] ])
    
    fortiss_rpc.destroy()
    
    template = "Room.html"

    

    if rooms != None:

  
        context = {'rooms': rooms, 'room': room, 'sensorhrnamelist': sensorhrnamelist}
        return render(request, template, context)
        return "test"
    else:
        return "empty"
    
    
def HistoricData(request):
    
    currentURL = request.path
    print (request.path)
    array = currentURL.split("/")
    target = array[(len(array)-2)]
    print (target)
    
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    rooms = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])
    room = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerHRName = '%s' LIMIT 0,30" % target])

    fortiss_rpc.destroy()
    
    template = "RoomHistoricData.html"


    if rooms != None:

  
        context = {'rooms': rooms, 'room': room,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"




def Controls(request):
    
    currentURL = request.path
    print (request.path)
    array = currentURL.split("/")
    target = array[(len(array)-2)]
    print (target)
    
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    rooms = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])
    room = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerHRName = '%s' LIMIT 0,30" % target])
    devices = fortiss_rpc.call("getSQLResults", ["SELECT * FROM ContainerManager_ContainerEdges WHERE ContainerType != 'DEVICE' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    
    template = "RoomControls.html"


    if rooms != None:

  
        context = {'rooms': rooms, 'room': room,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"
    
 


def success(request):
    context = {}
    template = "Success.html"
    return render(request, template, context)

def Containermanager(request):
    
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "Containermanager.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"

def Prophet(request):
    
    
    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "Prophet.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"

def blank(request):

    queue = 'informationbroker-query-1'
    fortiss_rpc = FortissRpcClient(queue)

    response = fortiss_rpc.call("getSQLResults", ["SELECT ContainerHRName FROM ContainerManager_Containers WHERE ContainerType != 'DEVICE' AND ContainerType != 'DEVICEGATEWAY' LIMIT 0,30"])

    fortiss_rpc.destroy()
    
    print(request.META)
    
    
    template = "blank.html"


    if response != None:

  
        context = {'rooms': response,}
        return render(request, template, context)
        return "test"
    else:
        return "empty"
    
    


def layouttest(request):
    context = {}
    template = "layouttest.html"
    return render(request, template, context)

def layouttest2(request):
    context = {}
    template = "layouttest2.html"
    return render(request, template, context)

def layouttest3(request):
    context = {}
    template = "layouttest3.html"
    return render(request, template, context)

def layouttest4(request):
    context = {}
    template = "layouttest4.html"
    return render(request, template, context)


