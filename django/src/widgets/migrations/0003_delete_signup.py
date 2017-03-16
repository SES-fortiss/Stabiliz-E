# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('widgets', '0002_auto_20150226_1826'),
    ]

    operations = [
        migrations.DeleteModel(
            name='SignUp',
        ),
    ]
