# C-RATION

## Description

This is a simple configuration file server based on [@typesafe/config](https://github.com/typesafe/config "Config") and [@playframework/Play20](http://www.playframework.org/ "Play!"). The server will return config files based on a directory hierarchy you provide. That file hierarchy can serve up static files, or you can be more dynamic with it it.

### Dynamic Configuration

If a folder has a "shared.conf" or "shared.properties" or "shared.json" file in it, shared values can be provided there. These values can be used in config files. This is very helpful if you have config settings that differ based on some parameter, such as different running environments (think "development" and "production", for example).

The "shared.conf" format uses [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) to allow dynamic/shared configuration to be as dynamic as possible with a minimum of work on my part. :)

The shared config is organized in a hierarchical path structure, with the directory tree represented as prefixed path items on item keys. If you had a file like:

>    database.port=12345

... in a folder tree like:

```
*root*
|_website
|__prod
|___shared.conf
```

... then you could refer to `${website.prod.database.port}` in your config files. See? The file is in "website/prod", so that's converted to "website.prod." and prefixed on "database.port".

You could use this value in any file. For example:

```
<appConfig>
  <database>
    <host>localhost</host>
    <port>${website.prod.database.port}</port>
  </database>
</appConfig>
```



## Setup

This app assumes you have Play! installed. Run `play stage` from the app folder. Now you can run `./target/start` to run the app.

## Configuration

In `conf/application.conf` there is `configfiles.rootdir` which must point to the root of the directory hierarchy you wish to serve up. The default is `configfiles` within the app folder (not provided).

## Examples

###### LICENSE

MIT License 