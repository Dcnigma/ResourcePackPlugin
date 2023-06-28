# ResourcePackPlugin + AutoBan!
Just a simple Minecraft plugin to load a resource packs.
And ban Minecraft server scanners.

I want to fight back against those logins and disconnect bots that visit my server.
Thats why i added AutoBan, didn't want to write another plugin. 


If I look in my server log I see a few of these info messages:
[INFO]: com.mojang.authlib.GameProfile@1ce0b5ed[id=<null>,name=xxx,properties={},legacy=false] (/x.x.x.x:50412) lost connection: Disconnected
I don't like that so I added some AutoBan feature in my ResourcePack Plugin.

The plugin will check if the player logs in correct to the default world (config.xml), if player moves and after 20 ticks it will reset the count.
If there are more then 3 Disconnects they get autobanned.
<div align="center"><img src="https://raw.githubusercontent.com/Dcnigma/ResourcePackPlugin/2d9f88ef078a6f17545c4a5c3228c3a0903fdd11/Screenshot/banned.png" alt="startup" height="340"/></div>
 <div align="left"> 
  
<div align="center">
 YouTube Demo:

 [![DEMO](https://img.youtube.com/vi/RTJpwH9Wc5M/hqdefault.jpg)](https://youtu.be/RTJpwH9Wc5M) 
 
 <div align="left"> 

Still testing it, but it so far it works.

I added a new command:/bannedips

<div align="center"><img src="https://raw.githubusercontent.com/Dcnigma/ResourcePackPlugin/2d9f88ef078a6f17545c4a5c3228c3a0903fdd11/Screenshot/bannedip.png" alt="startup" height="340"/></div>


and made a help function: /rpp help

<div align="center"><img src="https://raw.githubusercontent.com/Dcnigma/ResourcePackPlugin/2d9f88ef078a6f17545c4a5c3228c3a0903fdd11/Screenshot/help.png" alt="startup" height="340"/></div>



This is my first Minecraft plugin!  

So its not the best haha!

You can use the /loadpack commands with a event or (I Think, Untested) Commandblock to load the resource pack at any time you like.

Set permisions for members or specials groups: group.default | is default

You can use these commands "/loadpack1" to "/loadpack5"

Example1:
<div align="center"><img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/command.png?raw=true" alt="command"/></div>
So you have 5 commands to load your resources packs. (No Warning Prompt for using a Resource Pack!)


<div align="center"><img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/menu.png?raw=true" alt="command"/><div align="center"></div>
 <div align="left"> 
Example2: Make a Menu and a alias to run the command "/Resourcespack"</div>
  
<div align="center"><img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/atstartup.png?raw=true" alt="startup"/></div>
 <div align="left"> 
Example3: At startup using event-join plugin. (Warning Prompt for using Resource Pack!)</div>
  

<div align="center">
  <img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/server_resource.png?raw=true" alt="ResourcePackPlugin"  width="600" height="340" />
</div>
<div align="left"> 

There is one more command:
"/resourcepackplugin reload" to reload the "plugin.yml" after you made some changes


You can change the plugin.yml to your liking:

Example:  
  loadpack1:

    description: Load Redbull Resource Pack!

    usage: /loadpack1

    permission: group.default

    resource-pack-urls:

      - "https://example.com"


At the moment you can't change the Usage Commands. 

The only thing you can change are the permission and resource-pack-urls, 

The Description is optional is not used at this moment
</div>

Demo Shop Selling Redbull:
<div align="center">
  <img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/shopkeerpers.png?raw=true" alt="Shopkeerpers" height="300" />
 </div>


<div align="center">
  <img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/redbull.png?raw=true" alt="RedBullcan"/>
 
Cheers!</div>
