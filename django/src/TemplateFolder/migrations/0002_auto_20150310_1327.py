# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import datetime


class Migration(migrations.Migration):

    dependencies = [
        ('TemplateFolder', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='posts',
            old_name='title',
            new_name='password',
        ),
        migrations.RemoveField(
            model_name='posts',
            name='author',
        ),
        migrations.RemoveField(
            model_name='posts',
            name='bodytext',
        ),
        migrations.RemoveField(
            model_name='posts',
            name='timestamp',
        ),
        migrations.AddField(
            model_name='posts',
            name='username',
            field=models.CharField(default=datetime.date(2015, 3, 10), max_length=100),
            preserve_default=False,
        ),
    ]
