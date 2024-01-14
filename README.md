# AutomaticPlayerMovementPlugin
Minecraft plugin that will be useful as a utility for your purposes in the hub or game servers.

# Capabilities

- In what worlds will events performed by the plugin work
- Specify where the player will be teleported
- The minimum height at which the player will be returned to the coordinates you specified!
- What material will return the player to the coordinates you specified
- Where will the player look after moving?
- If you specify an inventory item check, teleportation will not occur!

# Commands

- `/apmp reload` - Plugin reloaded command

# Permissions

- `apmp.reload` - Plugin reloaded command permission

# Config
```
worldNames:
  - "world"
  - "another_world"
  - "yet_another_world"
x: 0
y: -60
z: 0
yaw: -180.0
pitch: 0.0
allowedMaterials:
  - "WATER"
  - "LAVA"
minY: -63.0
allowedMaterialsInventory:
  - RED_BED
```
