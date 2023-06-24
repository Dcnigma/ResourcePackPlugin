<div align="center">
  <img src="https://github.com/Dcnigma/ResourcePackPlugin/blob/main/Screenshot/server_resource.png?raw=true" alt="ResourcePackPlugin"  width="600" height="340" />
</div>

# ResourcePackPlugin
Just a simple Minecraft plugin to load a resource pack.

My first Minecraft plugin!
So its not the best haha!

You change the plugin.yml to your liking:
  
"  loadpack1:
"    description: Load Redbull Resource Pack!
"    usage: /loadpack1
"    permission: default
"    resource-pack-urls:
"      - "https://example.com"

At the moment you can't change the Usage Commands. 
The only thing you can change are the permission and resource-pack-urls, the Description is optional is not used.

That means you can only use these commands "/loadpack1" > "/loadpack5"
and "/resourcepackplugin reload" to reload the "plugin.yml" after you made some changes

#
#__________                                                 .__                    .___
#\______   \ ____   __________  __ _________   ____  ____   |  |   _________     __| _/___________
# |       _// __ \ /  ___/  _ \|  |  \_  __ \_/ ___\/ __ \  |  |  /  _ \__  \   / __ |/ __ \_  __ \
# |    |   \  ___/ \___ (  <_> )  |  /|  | \/\  \__\  ___/  |  |_(  <_> ) __ \_/ /_/ \  ___/|  | \/
# |____|_  /\___  >____  >____/|____/ |__|    \___  >___  > |____/\____(____  /\____ |\___  >__|
#        \/     \/     \/                         \/    \/                  \/      \/    \/
#
#  By Dcnigma v1.0 Snapshot!
#
# only the
#         resource-pack-urls can be changed
#   Usage cant be changed for now!
#
name: ResourcePackPlugin
version: 1.0.0
main: com.example.resourcepackplugin.ResourcePackPlugin
api-version: 1.17
author: Dcnigma
description: Simple ResourcePack Loader

commands:
  loadpack1:
    description: Load Blocktopolis Texture Pack 1
    usage: /loadpack1
    permission: default
#         resource-pack-urls and permission can be changed
# Usage cant be changed for now
    resource-pack-urls:
      - "https://example.com"
  loadpack2:
    description: Load Redbull Resource Pack!
    usage: /loadpack2
    permission: default
#         resource-pack-urls and permission can be changed
# Usage cant be changed for now
    resource-pack-urls:
      - "https://example.com"
  loadpack3:
    description: Load Blocktopolis Texture Pack 1
    usage: /loadpack3
    permission: default
#         resource-pack-urls and permission can be changed
# Usage cant be changed for now
    resource-pack-urls:
      - "https://example.com"
  loadpack4:
    description: Load Redbull Resource Pack!
    usage: /loadpack4
    permission: default
    resource-pack-urls:
      - "https://example.com"
  loadpack5:
    description: Load Redbull Resource Pack!
    usage: /loadpack5
    permission: default
#         resource-pack-urls and permission can be changed
# Usage cant be changed for now
    resource-pack-urls:
      - "https://example.com"
  ResourcePackPlugin:
    description: Reload plugin configuration
    usage: /ResourcePackPlugin reload
    permission: default
