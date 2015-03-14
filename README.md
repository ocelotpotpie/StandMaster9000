# StandMaster9000
Bukkit plugin to allow in-game editing of armor stand data.

## Usage
This plugin works by allowing players to make *modifications* to armor stand data through commands. Each modification changes a particular aspect of an armor stand; after you have made the modifications you want, the next armor stand you place will have the specified modifications. Note that modifications are evaluated in the order in which they were added, so if you add two modifications that modify the same property, the one you added most recently will be applied. Your current modification list can be viewed with "/stand list", or cleared with "/stand clear".

Info on all available modifications is in the [Commands](#commands) section.

## Presets
Presets are an easy way to apply several modifications at once. Anyone with the *standmaster.preset.add* permission can save their current modifier list to a preset with "/stand preset add \<name\>", which can then be loaded by anyone with the *stand.preset.load* permission using "/stand preset \<name\>". Other modifications can be added before or after loading a preset. Presets are removed with "/stand preset remove \<name\>" by anyone with the *standmaster.preset.remove* permission.

Presets are saved into and loaded from config.yml, under "presets.\<name\>". Therefore, they can also be edited by the server owner in a text editor. To reload the file once it's been edited, anyone with the *standmaster.reload* permission can run "/stand reload". A sample preset has been included to show you the YAML structure of these presets.

## Data Types
**BOOLEAN**: Either *true* or *false*. Any input other than *true* in a boolean field will default to *false*. Examples: true, false

**INT**: An integer. Examples: 0, 123, -456

**FLOAT**: A floating-point (decimal) value. Examples: 0, 3.14159, -0.0833

**STRING** A string of characters, or normal text. Examples: "Dumbo52", "StandMaster9000", "Hello world!" (without the quotes)

**ROTATION** A group of three floats, representing rotations (in degrees) around the X, Y, and Z axes respectively. Alternatively, a rotation along a single axis while leaving the other components unchanged can be achieved with "\<axis\> \<FLOAT\>". Examples: "90 0 180", "-45, 22.5, 270", "y 45.0"

## Commands
Help for any command can be accessed by just typing "/stand".

| Command                           | Permission                    | Description                             |
| --------------------------------- | ----------------------------- | --------------------------------------- |
| /stand reload                     | standmaster.reload            | Reloads the plugin's configuration      |
| /stand preset \<name\>            | standmaster.preset.load       | Loads a modifier preset                 |
| /stand preset add \<name\>        | standmaster.preset.add        | Adds a modifier preset                  |
| /stand preset remove \<name\>     | standmaster.preset.remove     | Removes a modifier preset               |
| /stand list                       | standmaster.stand.list        | Shows your current stand modifier list  |
| /stand clear                      | standmaster.stand.clear       | Clears your current stand modifier list |
| /stand name \<STRING\>            | standmaster.stand.name        | Gives the stand a visible nametag       |
| /stand invisible \<BOOLEAN\>      | standmaster.stand.invisible   | Makes the stand invisible               |
| /stand nobaseplate \<BOOLEAN\>    | standmaster.stand.nobaseplate | Removes the stand's baseplate           |
| /stand nogravity \<BOOLEAN\>      | standmaster.stand.nogravity   | Prevents the stand from falling         |
| /stand pose body \<ROTATION\>     | standmaster.stand.pose        | Sets the stand's body rotation          |
| /stand pose leftarm \<ROTATION\>  | standmaster.stand.pose        | Sets the stand's left arm rotation      |
| /stand pose rightarm \<ROTATION\> | standmaster.stand.pose        | Sets the stand's right arm rotation     |
| /stand pose leftleg \<ROTATION\>  | standmaster.stand.pose        | Sets the stand's left leg rotation      |
| /stand pose rightleg \<ROTATION\> | standmaster.stand.pose        | Sets the stand's right leg rotation     |
| /stand pose head \<ROTATION\>     | standmaster.stand.pose        | Sets the stand's head rotation          |
| /stand showarms \<BOOLEAN\>       | standmaster.stand.showarms    | Shows arms on the stand                 |
| /stand small \<BOOLEAN\>          | standmaster.stand.small       | Makes the stand smaller                 |
