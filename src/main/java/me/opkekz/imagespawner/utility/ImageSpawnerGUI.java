package me.opkekz.imagespawner.utility;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.input.Keyboard;

import me.opkekz.imagespawner.init.ModItems;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ImageSpawnerGUI extends GuiScreen{

	    private final String title = "             Please select an image ";
	    private final String title2="                 to be processed!";
	    private GuiButton mButtonClose;
	    private GuiButton addButton;
	    private GuiLabel newImageLabel;

	    @Override
	    public void initGui() {
	        super.initGui();
	        this.buttonList.add(addButton = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Select image"));
	        this.buttonList.add(mButtonClose = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 20, "Close"));
	        this.labelList.add(newImageLabel = new GuiLabel(fontRenderer, 2, this.width / 2 - 103, this.height / 2 - 40, 300, 20, 0xFFFFFF));

	        newImageLabel.addLine(title);
	        newImageLabel.addLine(title2);

	    }

	    @Override
	    protected void actionPerformed(GuiButton button) throws IOException {
	        if (button == mButtonClose) {
	            mc.player.closeScreen();
	        } else if (button == addButton) {
	        	updateImageName(getPathFromUser());
	        	mc.player.closeScreen();
	        }
	    }
	    
	    /**
	     * The user can select an image to be processed
	     * @return a String containing the absolute path to the selected image
	     */
	    private String getPathFromUser() {
	    	try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
			}
	    	JFileChooser jfc = new JFileChooser();
	        jfc.showDialog(null,"Select an image");
	        jfc.setVisible(true);
	        File image = jfc.getSelectedFile();
	        if (image != null) {
	        	return image.getAbsolutePath();
	        }
	        return null;
	    }
	    
	    private void updateImageName(String name) {
	    	
        	if (name != null && name.length() > 1 && name.contains(".")) {
        		ModItems.imagespawner.setImageName(name);
        		ModItems.imagespawner.initColorMatrix();
        	}
        	
	    }
	    
	    public void updateScreen()
	    {
	        super.updateScreen();
	    }

	    @Override
	    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	        this.drawDefaultBackground();
	        super.drawScreen(mouseX, mouseY, partialTicks);
	    }
	    
	    protected void mouseClicked(int x, int y, int btn) {
	        try {
				super.mouseClicked(x, y, btn);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @Override
	    public boolean doesGuiPauseGame() {
	        return true;
	    }
	
}
