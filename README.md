# project-base

ProjectBase is an Open Source Android library that allows developers to easily create applications.

## Version

**version 0.0.2**

- update isLogger

## Installation

Maven
```xml
<dependency>
  <groupId>com.github.rudnaruk</groupId>
  <artifactId>project-base</artifactId>
  <version>0.0.2</version>
  <type>pom</type>
</dependency>
```

App Gradle
```gradle
api('com.github.rudnaruk:project-base:0.0.2@aar'){
    exclude group: 'com.android.support', module: 'appcompat-v7'
}
```
Project Gradle
add to the end of file
```gradle
ext {
    compileSdkVersion = 27
    supportLibVersion = "27.1.1"
}
```

# Licence

Copyright 2018 piyaponf

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


Follow [facebook.com/rudnaruk](https://www.facebook.com/rudnaruk) on Facebook page.
or [@rudnaruk](https://medium.com/@ssaraleaw) at my Medium blog. :)

For contact, shoot me an email at rudnaruk@gmail.com
