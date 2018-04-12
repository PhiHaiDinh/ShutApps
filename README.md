# ShutApps

Die mobile Anwendung "ShutApps" enstand während meines Studiums "Wirtschaftsinformatik" in dem Modul Praxisprojekt in Zusammenarbeit mit Duc Giang Le. Die Grundidee dieses Projekts wurde als Ableitung für meine Bachelorarbeit entnommen. Hierbei war Duc Giang zum größten Teil für die Umsetzung und Implementierung der Android Anwendung zuständig, während ich mich hauptsächlich für die konzeptionellen Modellierungen befasst habe. 

Das Ziel dieses Projekts ist in Erfahrung zu bringen, ob es möglich ist, mit einer mobilen Anwendung die Smartphone-Nutzung auf den Smartphones von Freunden einzuschränken. Hierfür sollen die Nutzern gegenseitig bestimmte Apps auf dem Smartphone von Freunden blockieren können, wenn sie in der Nähe sind. Die Voraussetzung für die Nutzung der App ist, dass die Freunden diese App bewusst nutzen. 

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
     
