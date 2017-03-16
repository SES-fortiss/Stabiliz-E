from django.contrib import admin
from .models import realuser
from .models import UserProfile


class realuserAdmin(admin.ModelAdmin):
    list_display = ['firstname','lastname']
    class Meta:
        model = realuser
        
        
class UserProfileAdmin(admin.ModelAdmin):
    list_display = ['user']
    class Meta:
        model = UserProfile
        
        
admin.site.register(realuser)
admin.site.register(UserProfile, UserProfileAdmin)