# ShutApps

Die mobile Anwendung "ShutApps" enstand während meines Studiums "Wirtschaftsinformatik" im Modul Praxisprojekt in Zusammenarbeit mit Duc Giang Le. Die Grundidee dieses Projekts wurde als Fundament für meine Bachelorarbeit genommen. Hierbei war Duc Giang zum größten Teil für die Umsetzung und Implementierung der Android Anwendung zuständig, während ich mich hauptsächlich für die konzeptionellen Modellierungen befasst habe. 

Das Ziel des Projektes ist in Erfahrung zu bringen, ob es möglich ist, mit einer mobilen Anwendung die Smartphone-Nutzung von Freunden einzuschränken. Hierfür sollen die Freunde gegenseitig bestimmte Apps auf dem Smartphone des Freundes blockieren können, wenn sie in der Nähe sind. 

Genauere Informationen finden Sie im [Projektdokumentation](https://github.com/PhiHaiDinh/ShutApps/blob/master/Projektdokumentation.pdf)

## Architektur und Technologien
![Architekturdiagramm](https://github.com/ducle07/shutapps/blob/master/Architektur.png)

* **Android Studio** als Entwicklungsumgebung
* auf **Firebase** basierte Architektur, kein Server vorhanden
  * **Firebase Realtime Database** für Echtzeit-Datenhaltung
  * **Firebase Storage** zur Speicherung von Bildern (Profilbilder, App-Icons)
  * **Firebase Authentication** zur Authentifizierung in der Anwendung
* Client als mobiler Client in **Android**
  * **Accessibility-Service** zur Erkennung der Vordergrund-App
  * **NotificationListenerService** zur Unterdrückung der ankommenden Benachrichtigungen
* Anbindung von Facebook mit der **Facebook Graph API**
* Client zu Client Kommunikation vorher mit **Google Nearby Messages API**, jetzt mit **Android Beacon Library**
     
-----------------
-----------------

## English Version


The mobile application "ShutApps" was created during my study "Business Information Systems" in the module "Praxisprojekt" and in cooperation with Duc Giang Le. The basic idea of this project was taken as the foundation for my bachelor thesis. Duc Giang was responsible for most of the implementation of the Android application, while I was mainly involved in conceptual modeling.

The objective of the project is to find out if it is feasible to restrict the use of smartphones by friends with a mobile application. To achieve this, it is necessary that the friends who are nearby have the possibility to block friends specific apps on their smartphones.
