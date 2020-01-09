package me.opkekz.imagespawner.utility;

import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ImageSpawnerCommand extends CommandBase{

	private final String COMMAND_NAME = "newimage";
    private final String COMMAND_HELP = "Select a new image to build";

    public String getCommandName() {
        return COMMAND_NAME;
    }

    public String getCommandUsage(ICommandSender sender) {
        return COMMAND_HELP;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().displayGuiScreen(new ImageSpawnerGUI());
    }

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return COMMAND_HELP;
	}
	
}
