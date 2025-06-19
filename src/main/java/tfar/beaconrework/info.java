package tfar.beaconrework;

public class info {
    //Construction:
    //     The blocks you use to build the beacon base will determine the effects you have access to. All effects can be available at once,
    //     provided that the minimum requirements for each effect are met.
    //
    //Building Blocks:
    //Copper - Jump Boost 1, Speed 1
    //Iron - Resistance 1, Haste 1
    //Emerald - Jump Boost 2, Strength 1
    //Gold - Haste 2, Strength 2
    //Diamond - Regeneration 2, Speed 2
    //Netherite - Strength 3, Haste 3
    //Enderite - Regeneration 3, Resistance 2
    //
    //     To receive the desired effect, at least one layer must be made entirely out of that block. The number of layers
    //     used will determine the beacon range. A layer made out of a mixture of blocks will provide no effect,
    //     but will still add to the range. Having 2 or more layers of the same block will provide no additional benefit, but will still add to the range.
    //
    //Layer Range:
    //1 - 32 blocks
    //2 - 64 blocks
    //3 - 96 blocks
    //4 - 128 blocks
    //5 - 160 blocks
    //6 - 192 blocks
    //7 - 224 blocks
    //---------------------------------------------------------------------------
    //Payment:
    //     Payment will determine how long the beacon is on for. After the time runs out, the beacon shuts off.
    //     There is no slot in the gui for payment, the player will instead right click on the beacon block with their choice of payment to activate it.
    //
    //Payment Types:
    //Copper Ingot - 16 minutes
    //Iron Ingot -  32 minutes
    //Emerald - 48 minutes
    //Gold Ingot - 64 minutes
    //Diamond - 80 minutes
    //Netherite Ingot - 96 minutes
    //Enderite Ingot - 112 minutes
    //---------------------------------------------------------------------------
    //Max Beacon:
    //     A max beacon will provide all effects simultaniously at max range, and is constructed as follows.
    //
    //Layers (top to bottom):
    //Beacon x1
    //Enderite block x9
    //Netherite block x25
    //Diamond block x49
    //Gold block x81
    //Emerald block x121
    //Iron block x169
    //Copper block x225
    //---------------------------------------------------------------------------
    //Other Changes:
    //Six Beacon Pyramid - This should no longer be possible.
}
