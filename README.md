# InfinityLib

[![Build Status](https://Slimefun5.github.io/builds/Slimefun5/InfinityLib/stable/badge.svg)](https://Slimefun5.github.io/builds/Slimefun5/InfinityLib/stable)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/Slimefun5/InfinityLib/total)
[![GitHub Followers](https://img.shields.io/github/followers/Slimefun5?style=social)](https://github.com/Slimefun5)
[![GitHub Stars](https://img.shields.io/github/stars/Slimefun5/InfinityLib?style=social)](https://github.com/Slimefun5/InfinityLib)

A shared library providing utilities and APIs for Slimefun addons.

## Requirements
- Java 25
- Paper 1.16.* - 26.1.*
- Slimefun 5

## Core
<b>AbstractAddon</b>: An implementation of JavaPlugin
which you will need to extend for many of the other features to work.
It provides multiple utility methods and does some basic setup for you.

<b>AddonConfig</b>: is an implementation of YamlConfiguration
which makes comments available in the user's config
and provides utility methods such as getting a value from within a range
and removing unused/old keys from the user's config.

## Common
<b>CoolDowns</b>: A utility object for keeping track of cool downs of players/uuids

<b>PersistentType</b>: Contains some PersistentDataTypes for
ItemStack's, ItemStack Array's, Locations, and String Arrays.
Also provides a constructor for PersistentDataType that uses lambda parameters.

<b>Events</b>: Contains static utility methods for registering listeners, creating handlers, and calling events

<b>Scheduler</b>: Contains static utility methods for running and repeating tasks

## Commands
<b>AddonCommand</b>: allows you to add commands easily with a parent-child structure,
so you could have a command with a sub command which has a sub command.
It also adds some default commands such as an addon info, aliases, and help command.

## Groups
<b>MultiGroup</b>: An implementation of ItemGroup which lets you organize your groups into SubGroups

<b>SubGroup</b>: An ItemGroup that is hidden from the main page, for use in MultiGroup

## Machines
<b>MenuBlock</b>: A SlimefunItem with a menu, providing overridable methods for setting up the menu

<b>TickingMenuBlock</b>: A MenuBlock with slimefun ticker

<b>AbstractMachineBlock</b>: A TickingMenuBlock which implements EnergyNetComponent and provides a process method

<b>MachineBlock</b>: An AbstractMachineBlock which makes it easy to create simple input-output machines

## Future Additions
<b>Translation Utility</b>: Some sort of easy way to create translatable strings for your addon's and infinitylibs's strings

<b>InfinityLib Metrics</b>: Metrics to see which versions or even classes are being used

# How to use

First you need to add InfinityLib to the `dependencies` section in your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.mooy1</groupId>
    <artifactId>InfinityLib</artifactId>
    <version>SPECIFY VERSION HERE</version>
    <scope>compile</scope>
</dependency>
```

Then you need to relocate it into your own package so that it doesn't conflict with other addon's classes.

Under the `build` section in your `pom.xml`, you should have the following:

```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.4</version>
        <configuration>
            <!-- This will exclude any unused classes from libraries to reduce file size, not required -->
            <minimizeJar>true</minimizeJar>
            <relocations>
                <!-- This is the relocation, make sure to replace the package name, REQUIRED -->
                <relocation>
                    <pattern>io.github.mooy1.infinitylib</pattern>
                    <shadedPattern>YOUR.MAIN.PACKAGE.HERE.infinitylib</shadedPattern>
                </relocation>
            </relocations>
            <filters>
                <filter>
                    <artifact>*:*</artifact>
                    <excludes>
                        <exclude>META-INF/*</exclude>
                    </excludes>
                </filter>
            </filters>
        </configuration>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>shade</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

Then change your main plugin class to extend `AbstractAddon` and implement the constructor.
You will need to use `enable()` and `disable()` instead of `onEnable()` and `onDisable`.
Make sure you don't call `super.onEnable/Disable`.
Your updater and config setup is now handled, make sure to test that it's working though!

## Developer API

You can easily depend on this project using [github-gradle](https://github.com/intisy/github-gradle).

In your `build.gradle.kts`:

```kotlin
plugins {
    id("io.github.intisy.github-gradle") version "1.8.2.1"
}

dependencies {
    "githubCompileOnly"("Slimefun5:InfinityLib:v1.3.11")
}
```
