/* Project Name: Image Compression*/

import java.io.*;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Main extends JFrame{
	
	static File selectedFile;
	
	public static void main(String args[])
	{
		createGUI();
	}
	
	private static void createGUI()
	{
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Image Compression");
		guiFrame.setSize(600, 400);
		GridLayout grid = new GridLayout(2,2,10,10);
		guiFrame.setLayout(grid);
		guiFrame.setLocationRelativeTo(null);
		JTextField path = new JTextField("Choose the Image file to compress(or)decompress:");

		JButton browse = new JButton("Browse");
		browse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(chooseFile()){
					path.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		JButton convert = new JButton("Compress!");
		convert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				int result = compressImage(selectedFile);
				if(result==0)
				{
					JOptionPane.showMessageDialog(null, "Could not compress image: "+selectedFile.getAbsolutePath());
				}else{
					JOptionPane.showMessageDialog(null, "Image Compression successful!\nDestination Folder: "+selectedFile.getParent());
				}
			}
		});
		JButton decompress = new JButton("Decompress!");
		decompress.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				int result = decompressImage(selectedFile);
				if(result==0)
				{
					JOptionPane.showMessageDialog(null, "Could not decompress image: "+selectedFile.getAbsolutePath());
				}else{
					JOptionPane.showMessageDialog(null, "Image Decompression successful!\nDestination Folder: "+selectedFile.getParent());
				}
			}
		});
		guiFrame.add(path);
		guiFrame.add(browse);
		guiFrame.add(convert);
		guiFrame.add(decompress);
		guiFrame.setVisible(true);
	}
	
	private static boolean chooseFile(){
		JFileChooser fileChooser = new JFileChooser();
	    int returnValue = fileChooser.showOpenDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION) 
	    {
	    	selectedFile = fileChooser.getSelectedFile();
	    	System.out.println("File Selected: "+selectedFile.getName());
	    	return true;
	    }
	    return false;
	}
	
	private static int compressImage(File f)
	{
		int width=650;
		int height=674;
		BufferedImage image=null;

			try
			{
				image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
				image= ImageIO.read(f);
				System.out.println("Image Read Completed!");
			}
			catch(IOException e)
			{
				System.out.println("error"+e);
				return 0;
			}
			try
			{
				File c= new File(f.getParent()+"\\compress2.jpg");
				OutputStream os =new FileOutputStream(c);
				Iterator<ImageWriter> writers=ImageIO.getImageWritersByFormatName("jpg");
				ImageWriter writer = (ImageWriter) writers.next();
				ImageOutputStream ios=ImageIO.createImageOutputStream(os);
				writer.setOutput(ios);
				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.7f);
				writer.write(null,new IIOImage(image,null,null),param);
				System.out.println("Output done! Image created at "+f.getParent());
				System.out.println("Shutting down application!");
				os.close();
				ios.close();
				writer.dispose();
				return 1;
			}
			catch(IOException e)
			{	
			System.out.println("error"+e);
			return 0;
			}
	}
	
	private static int decompressImage(File f)
	{
		int width=650;
		int height=674;
		BufferedImage image=null;

			try
			{
			image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
			image= ImageIO.read(f);
			System.out.println("Image Read Completed!");
			}
			catch(IOException e)
			{
			System.out.println("erroe"+e);
			return 0;
			}
			try
			{
			 File c= new File(f.getParent()+"\\decompress.jpg");
			OutputStream os =new FileOutputStream(c);
			Iterator<ImageWriter> writers=ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios=ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.00f);
			writer.write(null,new IIOImage(image,null,null),param);
			System.out.println("Output done! Image created at "+f.getParent());
			System.out.println("Shutting down application!");
			os.close();
			ios.close();
			writer.dispose();
			return 1;
			}
			catch(IOException e)
		{	
			System.out.println("error"+e);
			return 0;
			}
	}
}
