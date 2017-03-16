from django.contrib import admin

# Register your models here.
from .models import WidgetData, Info

#register Informations about Energy Saving - Info model
class InfoAdmin(admin.ModelAdmin):
    class Meta:
        model = Info
        
admin.site.register(Info, InfoAdmin)

#register overall data of widgets - WidgetData model
class WidgetDataAdmin(admin.ModelAdmin):
    class Meta:
        model = WidgetData
     
admin.site.register(WidgetData, WidgetDataAdmin)
