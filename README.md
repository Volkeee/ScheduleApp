# Android IFNTUOG Schedule application
This application is a course project, made by students of IFNTUOG, located in Ivano-Frankivsk, Ukraine. This project aims to create an application to help out students in their studying process by holding their schedule (taken from rozklad.nung.edu.ua website) and their handly created tasks.
# Introduction
In order to try this application you will need either
* Pull this project and compile it for your device
* Install it onto your phone
* Login to the app with either your Google Account or your Facebook account
* The rest of the functionality is TBD

No worries about your personal data! The only information this app collects are your email address and public name, that\`s all!
# Schedule web-site and Own Server
This app uses [IFNTUOG\`s schedule][4]  website as a source of the schedule itself and uses [own Ruby on Rails-based server][5] to hold the informmation about tokens, devices, personal tasks etc.
# External libraries 
This project uses several libraries according to Apache License. For example, the [SearchableSpinner][1] and [MaterialSpinner][2]. The Custom* classes has been created by copying and modifying the source code from corresponding Github projects. All the credits for the external libraries belongs to their authors, I have no rights for that code, only for modyfied parts.

The networking part of the application has been made with use of [Volley][3] library.
# Licensing

Copyright 2017 Vovk Viktor

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[1]: https://goo.gl/knZb5e
[2]: https://goo.gl/gRtGef
[3]: https://goo.gl/G9XTvw
[4]: https://rozklad.nung.edu.ua/
[5]: https://github.com/Volkeee/schedule-api
