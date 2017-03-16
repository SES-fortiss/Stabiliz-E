from django.conf.urls import patterns, include, url
from django.contrib import admin
from test_project.views import testarea
from django.conf import settings
from django.conf.urls.static import static


urlpatterns = patterns('',
    # Examples:
    url(r'^$', 'test_project.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^accounts/', include('registration.backends.default.urls')),
    url(r'^start/$', 'test_project.views.start', name = 'start'),
    url(r'^testarea/$', testarea),
    url(r'^layouttest/$', 'test_project.views.layouttest'),
    url(r'^layouttest2/$', 'test_project.views.layouttest2'),
    url(r'^layouttest3/$', 'test_project.views.layouttest3'),
    url(r'^layouttest4/$', 'test_project.views.layouttest4'),
    url(r'^registration/$', 'joins.views.registration', name='registrations'),
    url(r'^Userpage/$', 'test_project.views.Userpage'),
    url(r'^Userpage/MyRooms/$', 'test_project.views.MyRooms'),
    url(r'^Userpage/Settings/$', 'test_project.views.Settings'),
    url(r'^Userpage/Devices/$', 'test_project.views.Devices'),
    url(r'^Userpage/fortiss/$', 'test_project.views.fortiss'),
    url(r'^Userpage/Containermanager/$', 'test_project.views.Containermanager'),
    url(r'^Userpage/Prophet/$', 'test_project.views.Prophet'),
    url(r'^Userpage/blank/$', 'test_project.views.blank'),
    url(r'^Userpage/Rooms/$', 'test_project.views.Room'),
    url(r'^Userpage/Rooms/Controls$', 'test_project.views.Room'),
    url(r'^Userpage/Rooms/Historic Data$', 'test_project.views.Room'),
    url(r'^Login/$', 'joins.views.Login'),
    url(r'^registration/Success/$', 'test_project.views.success'),
    url(r'^Userpage/Rooms/fortiss/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Floor -1/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Floor 0/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Floor 2/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Roof/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Utilityroom/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Serverroom/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 224/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 225/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Eastkitchen/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Room 206/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Eastwing/$', 'test_project.views.Room', name='login'),
    url(r'^Userpage/Rooms/Westwing/$', 'test_project.views.Room', name='login'),
    
    url(r'^Userpage/Rooms/HistoricData/fortiss/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor -1/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor 0/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Floor 2/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Roof/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Utilityroom/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Serverroom/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 224/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 225/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Eastkitchen/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Room 206/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Eastwing/$', 'test_project.views.HistoricData', name='login'),
    url(r'^Userpage/Rooms/HistoricData/Westwing/$', 'test_project.views.HistoricData', name='login'),
    
    url(r'^Userpage/Rooms/Controls/fortiss/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor -1/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor 0/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Floor 2/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Roof/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Utilityroom/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Serverroom/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 224/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 225/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Eastkitchen/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Room 206/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Eastwing/$', 'test_project.views.Controls', name='login'),
    url(r'^Userpage/Rooms/Controls/Westwing/$', 'test_project.views.Controls', name='login'),
    
    
)


if settings.DEBUG:
    urlpatterns += static(settings.STATIC_URL,
                          document_root=settings.STATIC_ROOT)
