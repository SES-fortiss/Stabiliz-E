from django.conf.urls import patterns, include, url
from django.contrib import admin
from LivingLabFrontend.views import testarea
from django.conf import settings
from django.conf.urls.static import static


urlpatterns = patterns('',




    url(r'^start/$', 'LivingLabFrontend.views.start', name = 'start'),
    url(r'^testarea/$', testarea),
    
    url(r'^$', 'LivingLabFrontend.views.WelcomePage', name='home'),                  # Welcomepage                        
    url(r'^registration/$', 'LivingLabFrontend.views.registration', name='registrations'),  # Registration          (TBD) 
    url(r'^Userpage/$', 'LivingLabFrontend.views.Userpage'),                         # Userpage              (Lacks Userverification)
    url(r'^Userpage/MyRooms/$', 'LivingLabFrontend.views.MyRooms'),                  # Roompage
    url(r'^Userpage/Settings/$', 'LivingLabFrontend.views.Settings'),                # Settings              (TBD)
    url(r'^Userpage/Devices/$', 'LivingLabFrontend.views.Devices'),                  # Devices               (Missing Functionalities)
    url(r'^Userpage/fortiss/$', 'LivingLabFrontend.views.fortiss'),                  # fortiss               (TBD)
    url(r'^Userpage/Containermanager/$', 'LivingLabFrontend.views.Containermanager'),# Containermanager      (TBD)
    url(r'^Userpage/Prophet/$', 'LivingLabFrontend.views.Prophet'),                  # Prophet               (TBD)
    url(r'^Userpage/blank/$', 'LivingLabFrontend.views.blank'),                      # blank
    url(r'^Userpage/Rooms/$', 'LivingLabFrontend.views.Room'),                       # Roompage              (TBD)
    url(r'^Userpage/Rooms/Controls$', 'LivingLabFrontend.views.Room'),               # Roomcontrols          (Missing Functionalities)
    url(r'^Userpage/Rooms/Historic Data$', 'LivingLabFrontend.views.Room'),          # Roomhistoricdata      (Missing Functionalities)
    url(r'^Login/$', 'LivingLabFrontend.views.Login'),                                      # Loginpage             (Lacks user verification)
    url(r'^registration/Success/$', 'LivingLabFrontend.views.success'),              # SuccessPage
    
    
    # All Rooms: 
    
    url(r'^Userpage/Rooms/fortiss/$', 'LivingLabFrontend.views.Room', name='login'), 
    url(r'^Userpage/Rooms/Floor -1/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Floor 0/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Floor 2/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Roof/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Utilityroom/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Serverroom/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 224/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 225/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Eastkitchen/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 206/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Eastwing/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/Westwing/$', 'LivingLabFrontend.views.Room', name='login'),
    url(r'^Userpage/Rooms/ExampleBuilding/$','LivingLabFrontend.views.Room', name='login'),
    
    
    # All Historicdatapages:
    
    url(r'^Userpage/Rooms/HistoricData/fortiss/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor -1/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor 0/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor 2/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Roof/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Utilityroom/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Serverroom/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 224/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 225/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Eastkitchen/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 206/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Eastwing/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Westwing/$', 'LivingLabFrontend.views.HistoricData', name='login'),
    
    
    # All Controlpages:
    
    url(r'^Userpage/Rooms/Controls/fortiss/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor -1/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor 0/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor 2/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Roof/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Utilityroom/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Serverroom/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 224/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 225/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Eastkitchen/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 206/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Eastwing/$', 'LivingLabFrontend.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Westwing/$', 'LivingLabFrontend.views.Controls', name='login'),
    
    
)


if settings.DEBUG:
    urlpatterns += static(settings.STATIC_URL,
                          document_root=settings.STATIC_ROOT)
