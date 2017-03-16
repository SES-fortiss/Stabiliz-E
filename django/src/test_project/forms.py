from django import forms
from joins.models import realuser

class RegistrationForm(forms.Form):
    username = forms.CharField(max_length = 30)
    password = forms.CharField(max_length = 30)
    
class LoginForm(forms.Form):
    username = forms.CharField(max_length = 30)
    password = forms.CharField(max_length = 30)
    
class JoinForm(forms.ModelForm):
    class Meta:
        model = realuser