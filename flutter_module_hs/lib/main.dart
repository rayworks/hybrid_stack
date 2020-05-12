import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';

void main() => runApp(MyApp());

const MethodChannel methodChannel =
    const MethodChannel("com.rayworks.hybridstack/method_channel");

class DemoText extends StatelessWidget {
  _buildBaseRoute() {
    return MaterialApp(
      title: 'Flutter text',
      home: Container(
        decoration: BoxDecoration(color: Colors.white),
        child: Center(
          child: RaisedButton(
            onPressed: () {
              print("f2n : cmd - " + "jump");
              methodChannel.invokeMethod("jump");

              /*FlutterBoost.singleton
                  .open('native')
                  .then((Map<dynamic, dynamic> value) {
                print(
                    'call me when page is finished. did recieve native route result $value');
              });*/
            },
            child: Text(
              "Test widget in flutter page",
              style: TextStyle(color: Colors.blue, fontSize: 20.0),
            ),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: _buildBaseRoute(),
    );
  }
}

class AppState extends State<MyApp> {
  int type = 0;

  AppState(int type);

  Future<dynamic> _handleMethodCall(MethodCall call) async {
    print("global_method_channel received ${call.method} ${call.arguments}");
    switch (call.method) {
      case "gotoRoute":
        //_initialize(call.arguments);
        setState(() {
          type = call.arguments;
        });
        break;
    }
  }

  @override
  void initState() {
    // NB: methodChannel should still work for non-page navigation logic
//    methodChannel.setMethodCallHandler(_handleMethodCall);

    // Due to the change on 'flutter activity', it seems we have to use the
    // boost's registered handler to manage the route navigation.
    FlutterBoost.singleton.registerPageBuilders({
      'init': (pageName, params, _) => MyHomePage(),
      'gotoRoute': (pageName, params, _) => MyHomePage(),
      'gotoDemo': (pageName, params, _) => DemoText(),
      'flutterPage': (String pageName, Map<dynamic, dynamic> params, String _) {
        print('flutterPage params:$params');

        var index;
        if (params.containsKey("gotoRoute")) {
          index = int.parse(params["gotoRoute"]);
          print(">>> widget index from param : $index");
        } else {
          index = Random().nextInt(2);
          print(">>> widget index from Random : $index");
        }

        return index == 0
            ? MyHomePage(
                title: "Flutter Demo title",
              )
            : DemoText();
      },
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    //return type == 0 ? DemoText() : _buildBaseRoute();
    return MaterialApp(
        title: 'Flutter Boost example',
        builder: FlutterBoost.init(postPush: _onRoutePushed),
        home: MyHomePage(
          title: "Flutter Title",
        )); //Container(color: Colors.white));//
  }

  void _onRoutePushed(
    String pageName,
    String uniqueId,
    Map<dynamic, dynamic> params,
    Route<dynamic> route,
    Future<dynamic> _,
  ) {}

  MaterialApp _buildMaterialApp() {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyApp extends StatefulWidget {
  int type = 0;

  @override
  State<StatefulWidget> createState() {
    return new AppState(type);
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });

    print("f2n : cmd - " + "jump");
    methodChannel.invokeMethod("jump");

    /*FlutterBoost.singleton.open('native').then((Map<dynamic, dynamic> value) {
      print(
          'call me when page is finished. did recieve native route result $value');
    });*/
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.display1,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
