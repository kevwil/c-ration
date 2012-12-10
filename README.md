# C-RATION

## Description

This is a simple http configuration file server based on [@typesafe/config](https://github.com/typesafe/config "Config") and [@playframework/Play20](http://www.playframework.org/ "Play!"). The server will return config files via http based on a directory hierarchy you provide. That file hierarchy can serve up static files, or you can be more dynamic with it it.

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

To retrieve this file, simply make an http call to your server & port with a `/config` prefix on the path. If your config file directory has `/blog/prod/mysql.ini` you would retrieve the file like this: `http://configserver:port/config//blog/prod/mysql.ini`.

## Setup

This app assumes you have Play! installed. Run `play stage` from the app folder. Now you can run `./target/start` to run the app.

## Configuration

In `conf/application.conf` there is `configfiles.rootdir` which must point to the root of the directory hierarchy you wish to serve up. The default is `configfiles` within the app folder (not provided).

## Examples

Given:

```
_root_
|-> blog
|---> prod
|-----> my.cnf
|-----> shared.conf
```

my.cnf:
```
...
[mysqld]
port=${blog.prod.mysql.port}
socket=${blog.prod.mysql.socket}
...
```

shared.conf:
```
mysql.port=3306
mysql.socket=/var/run/mysql.sock
```

You can retrieve your my.cnf file for the production blog with `wget http://server:port/config/blog/prod/my.cnf` or `curl -O http://server:port/config/blog/prod/my.cnf` (or any other means of downloading a file from http) and you will see:

```
...
[mysqld]
port=3306
socket=/var/run/mysql.sock
...
```

###### LICENSE

MIT License 