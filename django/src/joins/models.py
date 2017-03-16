from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class realuser(models.Model):
    
    firstname = models.CharField(max_length = 30)
    lastname = models.CharField(max_length= 30)

    def __unicode__(self):
        return "%s %s" %(self.firstname, self.lastname)
    
    
class UserProfile(models.Model):
    # This line is required. Links UserProfile to a User model instance.
    user = models.OneToOneField(User)

    # The additional attributes we wish to include.
    website = models.URLField(blank=True)
    picture = models.ImageField(upload_to='profile_images', blank=True)

    # Override the __unicode__() method to return out something meaningful!
    def __unicode__(self):
        return self.user.username