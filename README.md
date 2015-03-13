# StandMaster9000
Bukkit plugin to allow in-game editing of armor stand data.

## Commands
Help for any command can be accessed by just typing "/stand".

| Command                           | Permission                    | Description                             |
| --------------------------------- | ----------------------------- | --------------------------------------- |
| /stand reload                     | standmaster.reload            | Reloads the plugin's configuration      |
| /stand preset \<preset\>          | standmaster.preset.load       | Loads a modifier preset                 |
| /stand preset add \<preset\>      | standmaster.preset.add        | Adds a modifier preset                  |
| /stand preset remove \<preset\>   | standmaster.preset.remove     | Removes a modifier preset               |
| /stand list                       | standmaster.stand.list        | Shows your current stand modifier list  |
| /stand clear                      | standmaster.stand.clear       | Clears your current stand modifier list |
| /stand name \<STRING\>            | standmaster.stand.name        | Gives the stand a visible nametag       |
| /stand invisible \<BOOLEAN\>      | standmaster.stand.invisible   | Makes the stand invisible               |
| /stand nobaseplate \<BOOLEAN\>    | standmaster.stand.nobaseplate | Removes the stand's baseplate           |
| /stand nogravity \<BOOLEAN\>      | standmaster.stand.nogravity   | Prevents the stand from falling         |
| /stand pose body \<ROTATION\>*    | standmaster.stand.pose        | Sets the stand's body rotation          |
| /stand pose leftarm \<ROTATION\>  | standmaster.stand.pose        | Sets the stand's left arm rotation      |
| /stand pose rightarm \<ROTATION\> | standmaster.stand.pose        | Sets the stand's right arm rotation     |
| /stand pose leftleg \<ROTATION\>  | standmaster.stand.pose        | Sets the stand's left leg rotation      |
| /stand pose rightleg \<ROTATION\> | standmaster.stand.pose        | Sets the stand's right leg rotation     |
| /stand pose head \<ROTATION\>     | standmaster.stand.pose        | Sets the stand's head rotation          |
| /stand showarms \<BOOLEAN\>       | standmaster.stand.showarms    | Shows arms on the stand                 |
| /stand small \<BOOLEAN\>          | standmaster.stand.small       | Makes the stand smaller                 |
\* A ROTATION value can be set with either "/command \<x\> \<y\> \<z\>" or by setting rotation along a single axis (x, y, or z) with "/command \<axis\> \<value\>".
