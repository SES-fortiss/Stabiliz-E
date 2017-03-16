from django.conf.urls import patterns, include, url
from django.contrib import admin

from django.conf import settings
from django.conf.urls.static import static

from widgets import views

urlpatterns = patterns('',
    # Examples:
     #url(r'^$', views.BaseView.as_view(), name='home'),
     # url(r'^blog/', include('blog.urls')),
     #base page
     url(r'^$', 'widgets.views.baseview', name='home'),
     #about me page
     url(r'^about-me/$', 'widgets.views.aboutme', name='aboutme'),
     #admin interface
     url(r'^admin/', include(admin.site.urls)),
)

#urls for static and media files
if settings.DEBUG:
    urlpatterns += static(settings.STATIC_URL,
                          document_root=settings.STATIC_ROOT)
    urlpatterns += static(settings.MEDIA_URL,
                          document_root=settings.MEDIA_ROOT)
