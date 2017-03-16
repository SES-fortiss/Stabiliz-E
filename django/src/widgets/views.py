from django.shortcuts import render, render_to_response, RequestContext, HttpResponseRedirect, HttpResponse
from django.contrib import messages
from django.views import generic
from django.utils import timezone

from django.http import HttpResponse

from django.template import loader, Context, Template

from widgets.models import WidgetData
import datetime

# when getting real data from rpc client
#from fortiss.rpc.client import * 

#basic view - shows all widgets
def baseview(request):
    #refer HTML template
    template = loader.get_template('widgets.html')
        
    #define context with given data
    context = RequestContext(request, {
            'widgetdata_list': WidgetData.objects.all() #retrieve all entries from WidgetData table
            })
    #render template with defined context
    return render_to_response("widgets.html",
                              locals(),
                              context_instance=context)

# example of getting data from information broker
# here the percentage of the battery is retrieved
#def baseview(request):
    #template = loader.get_template('widgets.html')
     
    #queue = 'informationbroker-query-1'
    #fortiss_rpc = FortissRpcClient(queue)

    #response = fortiss_rpc.call("getSQLResults", ["SELECT devid, wrapperid, value, maxAbsError, timestamp FROM DoubleEvents WHERE devid='battery_percentage' order by timestamp desc LIMIT 0 , 1"])
    #fortiss_rpc.destroy()
    
    #if response != None:

        # give to template object
        #context = RequestContext(request, {
            #'battery': response, 'widgetdata_list': WidgetData.objects.all()
            #})
        #return render_to_response("widgets.html",
                              #locals(),
                              #context_instance=context)

        #return "test"
        
    #else:
    	#return "empty"

   
#view with information about author
def aboutme(request):
    #refer HTML template
    template = loader.get_template('aboutme.html')
    #render template
    return render_to_response("aboutme.html",
                              locals(),
                              context_instance=RequestContext(request))