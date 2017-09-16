package ua.kozak.solowork.service.impl;

import ua.kozak.solowork.service.StorageService;

import java.io.*;
import java.util.HashSet;

public class StorageServiceImpl<O> implements StorageService<O> {

    private String fileName;
    private String filePath = "src\\main\\resources\\storage\\";
    private File file;

    public StorageServiceImpl(String fileName) {
        this.create(fileName);
    }

    @Override
    public O write(O o) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read();
    }

    @Override
    public O read() {
        if (file.length() == 0) {
            return (O) new HashSet<>();
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (O) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public boolean create(String fileName) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        this.fileName = filePath + fileName + ".nathalie";
        this.file = new File(this.fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new HashSet<>());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
