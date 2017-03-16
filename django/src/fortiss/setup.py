import os
from setuptools import setup

README = open(os.path.join(os.path.dirname(__file__), 'README.rst')).read()

# allow setup.py to be run from any path
os.chdir(os.path.normpath(os.path.join(os.path.abspath(__file__), os.pardir)))

setup(
    name='fortissrpc',
    version='0.1',
    packages=['rpc'],
    include_package_data=True,
    license='BSD License',  # example license
    description='A simple RPC library for fortiss remote calls',
    long_description=README,
    url='http://www.fortiss.org',
    author='Sebastian Wilzbach.me',
    author_email='sebi@wilzbach.me',
    classifiers=[
        'Environment :: Web Environment',
        'Framework :: Django',
        'Intended Audience :: Developers',
        'License :: OSI Approved :: BSD License', # example license
        'Operating System :: OS Independent',
        'Programming Language :: Python',
    ],
)
