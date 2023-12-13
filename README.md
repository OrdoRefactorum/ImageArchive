# ImageArchive

This is a small application I put together to experiment with certain aspects of image handling. It basically just allows uploading pictures into a mysql database and loads the images from there on request. Subsequent request are cached. To leave out bells and whistles the upload is provided via thymeleaf templated pages.

## Goals

* Experiment a little with Thymeleaf
* Accomplish uploading pictures into a MySQL dataqbase
* Cache returned images instead of returning from filesystem / database at each request

## How-to-run

1. Download / clone the project
1. Create a database `image_archive`
1. Change username / password in the `applications.properties` files to your settings
1. Find some pics to upload
1. Done

## Used software

* Maven
* Java 17
* Spring Boot 3.2.0
* MySQL Server 8.2.0