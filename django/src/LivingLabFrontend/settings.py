"""
Django settings for LivingLabFrontend project.

For more information on this file, see
https://docs.djangoproject.com/en/1.7/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/1.7/ref/settings/
"""

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
import os
BASE_DIR = os.path.dirname(os.path.dirname(__file__))
print ("base dir is" + BASE_DIR)

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/1.7/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = 'lk08ux_$avv*6*=b$3p*geprlx^s3+6$n#%*2fwni(h4ng4w&c'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

TEMPLATE_DEBUG = DEBUG

ALLOWED_HOSTS = []


# Application definition

INSTALLED_APPS = (
    'django.contrib.admin',
    'django.contrib.auth',
  #  'django.contrib.sites',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'TemplateFolder',
    'registration',
    
)

ACCOUNT_ACTIVATION_DAYS = 7

MIDDLEWARE_CLASSES = (
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.auth.middleware.SessionAuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
)

ROOT_URLCONF = 'LivingLabFrontend.urls'

WSGI_APPLICATION = 'LivingLabFrontend.wsgi.application'


# Database
# https://docs.djangoproject.com/en/1.7/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'OPTIONS': {
            'read_default_file': 'my.cnf',
        },
        'NAME' : "django",  	# "django"
        'USER' : "fortiss",        # "root"
        'PASSWORD' : "foo",        # "" 	(empty)
        'HOST' : 'localhost',   # "localhost"
        'PORT' : 3306,			# 3306 	(not a String)
    }
}

# Internationalization
# https://docs.djangoproject.com/en/1.7/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


#TEMPLATE_DIRS = (
#    'C:\Users\Admin\Desktop\Bachelorarbeit\venv\src\blog\templates',
#)

# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/1.7/howto/static-files/

STATIC_URL = '/static/'

if DEBUG:
        # STATICFILES_DIRS = {
        #    os.path.join(os.path.dirname(BASE_DIR), "static", "static"),
        #                    }
        
        STATICFILES_DIRS = (
            os.path.join(BASE_DIR, "static"),
            '/static/images/',
        )
