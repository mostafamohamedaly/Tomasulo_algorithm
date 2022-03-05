import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<String> readTextFile(String filepath){
        ArrayList<String> result = new ArrayList<>();

        try (FileReader f = new FileReader(filepath)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    result.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                result.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        int cycles=0;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter add/sub exectute cycles :");
        int addsubcycles=sc.nextInt();
       // int addsubcycles=3;
        System.out.println("enter load exectute cycles :");
        int loadcylces=sc.nextInt();
      //  int loadcylces=1;
        System.out.println("enter store exectute cycles :");
        int storecycles=sc.nextInt();
        //int storecycles=1;
        System.out.println("enter mul/div exectute cycles :");
        int muldivcycles=sc.nextInt();
       // int muldivcycles=5;
        double memory[]=new double[128];
        for(int l=0;l<memory.length;l++){
            memory[l]=1;
        }
        Register[] Registers = new Register[32];
        ReservationStation a1=new ReservationStation("A1",false,null,null,null,null,null,addsubcycles);
        ReservationStation a2=new ReservationStation("A2",false,null,null,null,null,null,addsubcycles);
        ReservationStation a3=new ReservationStation("A3",false,null,null,null,null,null,addsubcycles);
        ReservationStation[] addsub=new ReservationStation[3];
        addsub[0]=a1;
        addsub[1]=a2;
        addsub[2]=a3;
        ReservationStation m1=new ReservationStation("M1",false,null,null,null,null,null,muldivcycles);
        ReservationStation m2=new ReservationStation("M2",false,null,null,null,null,null,muldivcycles);
        ReservationStation m3=new ReservationStation("M3",false,null,null,null,null,null,muldivcycles);
        ReservationStation[] muldiv=new ReservationStation[3];
        muldiv[0]=m1;
        muldiv[1]=m2;
        muldiv[2]=m3;
        LoadBuffer l1=new LoadBuffer("L1",false,0,loadcylces);
        LoadBuffer l2=new LoadBuffer("L2",false,0,loadcylces);
        LoadBuffer l3=new LoadBuffer("L3",false,0,loadcylces);
        LoadBuffer[] loadbuffers=new LoadBuffer[3];
        loadbuffers[0]=l1;
        loadbuffers[1]=l2;
        loadbuffers[2]=l3;
        StoreBuffer s1=new StoreBuffer("S1",false,0,null,null,storecycles);
        StoreBuffer s2=new StoreBuffer("S2",false,0,null,null,storecycles);
        StoreBuffer s3=new StoreBuffer("S3",false,0,null,null,storecycles);
        StoreBuffer[] storebuffers=new StoreBuffer[3];
        storebuffers[0]=s1;
        storebuffers[1]=s2;
        storebuffers[2]=s3;

        for (int i = 0; i < 32; i++) {
            String name = "R" + i;
            double p=0;
            Registers[i] = new Register(name, p);
        }
       ArrayList<String> instructions = readTextFile("src/instructions.txt");
        boolean stall=false;
        for(int i =0;i<instructions.size();i++){ //issue
           String inst= instructions.get(i);

            String[] words = inst.split(" ");
            int stationNo=-1;
            if(words[0].equals("ADD")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<addsub.length;j++){
                    ReservationStation tmp=addsub[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true){
                    addsub[ind].setBusy(true);
                    addsub[ind].setOpcode("ADD");
                   String destReg= words[1];
                   destReg = destReg.replaceAll("[^0-9]", "");
                   String qReg=words[2];
                    qReg = qReg.replaceAll("[^0-9]", "");
                   String kReg=words[3];
                    kReg = kReg.replaceAll("[^0-9]", "");
                    Register destRegister=Registers[Integer.parseInt(destReg)];
                    Register qRegg = Registers[Integer.parseInt(qReg)];
                    Register kRegg = Registers[Integer.parseInt(kReg)];
                    if(qRegg.getValue().getClass().getName().equals("java.lang.String")){
                        addsub[ind].setVj((String) qRegg.getValue());
                    }
                        else if(qRegg.getValue().getClass().getName().equals("java.lang.Double")){
                        addsub[ind].setQj(String.valueOf(qRegg.getValue()));
                    }
                    if(kRegg.getValue().getClass().getName().equals("java.lang.String")){
                        addsub[ind].setVk((String) kRegg.getValue());
                    }
                    else if(kRegg.getValue().getClass().getName().equals("java.lang.Double")){
                        addsub[ind].setQk(String.valueOf(kRegg.getValue()));
                    }
                    destRegister.setValue(addsub[ind].getName());
                    Registers[Integer.parseInt(destReg)]=destRegister;
                }
            }
            else if(words[0].equals("SUB")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<addsub.length;j++){
                    ReservationStation tmp=addsub[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true){
                    addsub[ind].setBusy(true);
                    addsub[ind].setOpcode("SUB");
                    String destReg= words[1];
                    destReg = destReg.replaceAll("[^0-9]", "");
                    String qReg=words[2];
                    qReg = qReg.replaceAll("[^0-9]", "");
                    String kReg=words[3];
                    kReg = kReg.replaceAll("[^0-9]", "");
                    Register destRegister=Registers[Integer.parseInt(destReg)];
                    Register qRegg = Registers[Integer.parseInt(qReg)];
                    Register kRegg = Registers[Integer.parseInt(kReg)];
                    if(qRegg.getValue().getClass().getName().equals("java.lang.String")){
                        addsub[ind].setVj((String) qRegg.getValue());
                    }
                    else if(qRegg.getValue().getClass().getName().equals("java.lang.Double")){
                        addsub[ind].setQj(String.valueOf(qRegg.getValue()));
                    }
                    if(kRegg.getValue().getClass().getName().equals("java.lang.String")){
                        addsub[ind].setVk((String) kRegg.getValue());
                    }
                    else if(kRegg.getValue().getClass().getName().equals("java.lang.Double")){
                        addsub[ind].setQk(String.valueOf(kRegg.getValue()));
                    }
                    destRegister.setValue(addsub[ind].getName());
                    Registers[Integer.parseInt(destReg)]=destRegister;
                }
            }
            else if(words[0].equals("MUL")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<muldiv.length;j++){
                    ReservationStation tmp=muldiv[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true) {
                    muldiv[ind].setBusy(true);
                    muldiv[ind].setOpcode("MUL");
                    String destReg = words[1];
                    destReg = destReg.replaceAll("[^0-9]", "");
                    String qReg = words[2];
                    qReg = qReg.replaceAll("[^0-9]", "");
                    String kReg = words[3];
                    kReg = kReg.replaceAll("[^0-9]", "");
                    Register destRegister = Registers[Integer.parseInt(destReg)];
                    Register qRegg = Registers[Integer.parseInt(qReg)];
                    Register kRegg = Registers[Integer.parseInt(kReg)];
                    if (qRegg.getValue().getClass().getName().equals("java.lang.String")) {
                        muldiv[ind].setVj((String) qRegg.getValue());
                    } else if (qRegg.getValue().getClass().getName().equals("java.lang.Double")) {
                        muldiv[ind].setQj(String.valueOf(qRegg.getValue()));
                    }
                    if (kRegg.getValue().getClass().getName().equals("java.lang.String")) {
                        muldiv[ind].setVk((String) kRegg.getValue());
                    } else if (kRegg.getValue().getClass().getName().equals("java.lang.Double")) {
                        muldiv[ind].setQk(String.valueOf(kRegg.getValue()));
                    }
                    destRegister.setValue(muldiv[ind].getName());
                    Registers[Integer.parseInt(destReg)] = destRegister;
                }
            }
            else if(words[0].equals("DIV")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<muldiv.length;j++){
                    ReservationStation tmp=muldiv[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true) {
                    muldiv[ind].setBusy(true);
                    muldiv[ind].setOpcode("DIV");
                    String destReg = words[1];
                    destReg = destReg.replaceAll("[^0-9]", "");
                    String qReg = words[2];
                    qReg = qReg.replaceAll("[^0-9]", "");
                    String kReg = words[3];
                    kReg = kReg.replaceAll("[^0-9]", "");
                    Register destRegister = Registers[Integer.parseInt(destReg)];
                    Register qRegg = Registers[Integer.parseInt(qReg)];
                    Register kRegg = Registers[Integer.parseInt(kReg)];
                    if (qRegg.getValue().getClass().getName().equals("java.lang.String")) {
                        muldiv[ind].setVj((String) qRegg.getValue());
                    } else if (qRegg.getValue().getClass().getName().equals("java.lang.Double")) {
                        muldiv[ind].setQj(String.valueOf(qRegg.getValue()));
                    }
                    if (kRegg.getValue().getClass().getName().equals("java.lang.String")) {
                        muldiv[ind].setVk((String) kRegg.getValue());
                    } else if (kRegg.getValue().getClass().getName().equals("java.lang.Double")) {
                        muldiv[ind].setQk(String.valueOf(kRegg.getValue()));
                    }
                    destRegister.setValue(muldiv[ind].getName());
                    Registers[Integer.parseInt(destReg)] = destRegister;
                }
            }
            else if(words[0].equals("LD")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<loadbuffers.length;j++){
                    LoadBuffer tmp=loadbuffers[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true){
                    loadbuffers[ind].setBusy(true);
                    String destReg = words[1];
                    destReg = destReg.replaceAll("[^0-9]", "");
                    String address = words[2];
                    address=address.replaceAll("[^0-9]", "");
                    Register destRegister = Registers[Integer.parseInt(destReg)];
                    loadbuffers[ind].setAddress(Integer.parseInt(address));
                    destRegister.setValue(loadbuffers[ind].getName());
                    Registers[Integer.parseInt(destReg)] = destRegister;
                }
            }
            else if(words[0].equals("SD")){
                int ind=0;
                boolean found=false;
                for(int j=0;j<loadbuffers.length;j++){
                    StoreBuffer tmp=storebuffers[j];
                    if(tmp.isBusy()==false){
                        ind=j;
                        found=true;
                        stationNo=j;
                        stall=false;
                        break;
                    }
                    stall=true;
                }
                if(found==true){
                    storebuffers[ind].setBusy(true);
                    String srcReg = words[1];
                    srcReg = srcReg.replaceAll("[^0-9]", "");
                    String address = words[2];
                    address=address.replaceAll("[^0-9]", "");
                    Register srcRegister = Registers[Integer.parseInt(srcReg)];
                    storebuffers[ind].setAddress(Integer.parseInt(address));
                    if(srcRegister.getValue().getClass().getName().equals("java.lang.String")){
                        storebuffers[ind].setVj((String) srcRegister.getValue());
                    }
                    else if(srcRegister.getValue().getClass().getName().equals("java.lang.Double")){
                        storebuffers[ind].setQj(String.valueOf(srcRegister.getValue()));
                    }

                }
            }
            //Execution
            if(words[0].equals("ADD") || words[0].equals("SUB")){
                for(int k=0;k<addsub.length;k++){
                    if(k==stationNo){
                        continue;
                    }
                    if(addsub[k].getQj()!=null && addsub[k].getQk()!=null && addsub[k].getCyclesRemaining()>0){
                        addsub[k].setCyclesRemaining(addsub[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<muldiv.length;k++){
                    if(muldiv[k].getQj()!=null && muldiv[k].getQk()!=null &&muldiv[k].getCyclesRemaining()>0){
                        muldiv[k].setCyclesRemaining(muldiv[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<loadbuffers.length;k++){
                    if(loadbuffers[k].getCyclesRemaining()>0 && loadbuffers[k].isBusy()){
                    loadbuffers[k].setCyclesRemaining(loadbuffers[k].getCyclesRemaining()-1);}
                }
                for(int k=0;k<storebuffers.length;k++){
                    if(storebuffers[k].getCyclesRemaining()>0 && storebuffers[k].getQj()!=null){
                        storebuffers[k].setCyclesRemaining(storebuffers[k].getCyclesRemaining()-1);
                    }
                }
            }
            else if(words[0].equals("MUL") ||words[0].equals("DIV")){
                for(int k=0;k<addsub.length;k++){
                    if(addsub[k].getQj()!=null && addsub[k].getQk()!=null && addsub[k].getCyclesRemaining()>0){
                        addsub[k].setCyclesRemaining(addsub[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<muldiv.length;k++){
                    if(k==stationNo){
                        continue;
                    }
                    if(muldiv[k].getQj()!=null && muldiv[k].getQk()!=null && muldiv[k].getCyclesRemaining()>0){
                        muldiv[k].setCyclesRemaining(muldiv[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<loadbuffers.length;k++){
                    if(loadbuffers[k].getCyclesRemaining()>0 && loadbuffers[k].isBusy()){
                    loadbuffers[k].setCyclesRemaining(loadbuffers[k].getCyclesRemaining()-1);}
                }
                for(int k=0;k<storebuffers.length;k++){
                    if(storebuffers[k].getCyclesRemaining()>0 && storebuffers[k].getQj()!=null){
                        storebuffers[k].setCyclesRemaining(storebuffers[k].getCyclesRemaining()-1);
                    }
                }

            }
            else if(words[0].equals("LD")){
                for(int k=0;k<addsub.length;k++){
                    if(addsub[k].getQj()!=null && addsub[k].getQk()!=null && addsub[k].getCyclesRemaining()>0){
                        addsub[k].setCyclesRemaining(addsub[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<muldiv.length;k++){
                    if(muldiv[k].getQj()!=null && muldiv[k].getQk()!=null && muldiv[k].getCyclesRemaining()>0){
                        muldiv[k].setCyclesRemaining(muldiv[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<loadbuffers.length;k++){
                    if(k==stationNo){
                        continue;
                    }
                    if(loadbuffers[k].getCyclesRemaining()>0 && loadbuffers[k].isBusy()){
                    loadbuffers[k].setCyclesRemaining(loadbuffers[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<storebuffers.length;k++){
                    if(storebuffers[k].getCyclesRemaining()>0 && storebuffers[k].getQj()!=null){
                        storebuffers[k].setCyclesRemaining(storebuffers[k].getCyclesRemaining()-1);
                    }
                }
            }
            else if(words[0].equals("SD")){
                for(int k=0;k<addsub.length;k++){
                    if(addsub[k].getQj()!=null && addsub[k].getQk()!=null && addsub[k].getCyclesRemaining()>0){
                        addsub[k].setCyclesRemaining(addsub[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<muldiv.length;k++){
                    if(muldiv[k].getQj()!=null && muldiv[k].getQk()!=null && muldiv[k].getCyclesRemaining()>0){
                        muldiv[k].setCyclesRemaining(muldiv[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<loadbuffers.length;k++){
                    if(loadbuffers[k].getCyclesRemaining()>0 && loadbuffers[k].isBusy()){
                        loadbuffers[k].setCyclesRemaining(loadbuffers[k].getCyclesRemaining() - 1);
                    }
                }
                for(int k=0;k<storebuffers.length;k++){
                    if(k==stationNo){
                        continue;
                    }
                    if(storebuffers[k].getCyclesRemaining()>0 && storebuffers[k].getQj()!=null){
                        storebuffers[k].setCyclesRemaining(storebuffers[k].getCyclesRemaining()-1);
                    }
                }
            }


            //Cycle printing
            System.out.println("After Cycle Number :" +cycles);
            cycles+=1;
            System.out.println("ADD/DIV Reservation Stations");
            System.out.println(addsub[0].toString());
            System.out.println(addsub[1].toString());
            System.out.println(addsub[2].toString());
            System.out.println("MUL/DIV Reservation Stations");
            System.out.println(muldiv[0].toString());
            System.out.println(muldiv[1].toString());
            System.out.println(muldiv[2].toString());
            System.out.println("Load Buffers");
            System.out.println(loadbuffers[0].toString());
            System.out.println(loadbuffers[1].toString());
            System.out.println(loadbuffers[2].toString());
            System.out.println("Store Buffers");
            System.out.println(storebuffers[0].toString());
            System.out.println(storebuffers[1].toString());
            System.out.println(storebuffers[2].toString());
            System.out.println("Register Files");
            for(int h=0;h<32;h+=4){
                System.out.print(Registers[h].toString());
                System.out.print(Registers[h+1].toString());
                System.out.print(Registers[h+2].toString());
                System.out.println(Registers[h+3].toString());
            }
            System.out.println("Memory");
            for (int o=0;o<memory.length;o+=32){
                System.out.print(memory[o]+",");
                System.out.print(memory[o+1]+",");
                System.out.print(memory[o+2]+",");
                System.out.print(memory[o+3]+",");
                System.out.print(memory[o+4]+",");
                System.out.print(memory[o+5]+",");
                System.out.print(memory[o+6]+",");
                System.out.print(memory[o+7]+",");
                System.out.print(memory[o+8]+",");
                System.out.print(memory[o+9]+",");
                System.out.print(memory[o+10]+",");
                System.out.print(memory[o+11]+",");
                System.out.print(memory[o+12]+",");
                System.out.print(memory[o+13]+",");
                System.out.print(memory[o+14]+",");
                System.out.print(memory[o+15]+",");
                System.out.print(memory[o+16]+",");
                System.out.print(memory[o+17]+",");
                System.out.print(memory[o+18]+",");
                System.out.print(memory[o+19]+",");
                System.out.print(memory[o+20]+",");
                System.out.print(memory[o+21]+",");
                System.out.print(memory[o+22]+",");
                System.out.print(memory[o+23]+",");
                System.out.print(memory[o+24]+",");
                System.out.print(memory[o+25]+",");
                System.out.print(memory[o+26]+",");
                System.out.print(memory[o+27]+",");
                System.out.print(memory[o+28]+",");
                System.out.print(memory[o+29]+",");
                System.out.print(memory[o+30]+",");
                System.out.println(memory[o+31]+", ");

            }

            System.out.println("_________________________________________________________");
            System.out.println("_________________________________________________________");
            System.out.println("_________________________________________________________");
            //Write back
            //Writing results from add-sub stations
            for(int l=0;l<addsub.length;l++){
                if(addsub[l].isBusy() && addsub[l].getCyclesRemaining()==0){
                    String name=addsub[l].getName();
                    double value=0;
                    if(addsub[l].getOpcode().equals("ADD")){
                        value=Double.parseDouble(addsub[l].getQj())+Double.parseDouble(addsub[l].getQk());
                    }else if(addsub[l].getOpcode().equals("SUB")){
                        value=Double.parseDouble(addsub[l].getQj())-Double.parseDouble(addsub[l].getQk());
                    }
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    addsub[l].setBusy(false);
                    addsub[l].setOpcode(null);
                    addsub[l].setQk(null);
                    addsub[l].setQj(null);
                    addsub[l].setVk(null);
                    addsub[l].setVj(null);
                    addsub[l].setCyclesRemaining(addsubcycles);
                }

            }
            //writing results from mul-div
            for(int l=0;l<muldiv.length;l++){
                if(muldiv[l].isBusy() && muldiv[l].getCyclesRemaining()==0) {
                    String name = muldiv[l].getName();
                    double value = 0;
                    if (muldiv[l].getOpcode().equals("MUL")) {
                        value = Double.parseDouble(muldiv[l].getQj()) * Double.parseDouble(muldiv[l].getQk());
                    } else if (muldiv[l].getOpcode().equals("DIV")) {
                        value = Double.parseDouble(muldiv[l].getQj()) / Double.parseDouble(muldiv[l].getQk());
                    }
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    muldiv[l].setBusy(false);
                    muldiv[l].setOpcode(null);
                    muldiv[l].setQk(null);
                    muldiv[l].setQj(null);
                    muldiv[l].setVk(null);
                    muldiv[l].setVj(null);
                    muldiv[l].setCyclesRemaining(muldivcycles);
                }
            }

            //writing results from load
            for(int l=0;l<loadbuffers.length;l++){
                if(loadbuffers[l].isBusy() && loadbuffers[l].getCyclesRemaining()==0) {
                    String name = loadbuffers[l].getName();
                    double value = memory[loadbuffers[l].getAddress()];
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    loadbuffers[l].setBusy(false);
                    loadbuffers[l].setCyclesRemaining(loadcylces);
                    loadbuffers[l].setAddress(0);
                }

            }


            for(int l=0;l<storebuffers.length;l++){
                if(storebuffers[l].isBusy() && storebuffers[l].getCyclesRemaining()==0){
                    double value =Double.parseDouble(storebuffers[l].getQj());
                    memory[storebuffers[l].getAddress()]=value;

                    storebuffers[l].setBusy(false);
                    storebuffers[l].setCyclesRemaining(storecycles);
                    storebuffers[l].setAddress(0);
                    storebuffers[l].setQj(null);
                    storebuffers[l].setVj(null);
                }
            }



            if(stall==true){
                i=i-1;
                stall=false;
            }


        }//continue after finish issuing
        boolean busyStation=true;
        while (busyStation){

                for(int k=0;k<addsub.length;k++){
                    if(addsub[k].isBusy() && addsub[k].getQj()!=null && addsub[k].getQk()!=null && addsub[k].getCyclesRemaining()>0){
                        addsub[k].setCyclesRemaining(addsub[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<muldiv.length;k++){
                    if(muldiv[k].isBusy() && muldiv[k].getQj()!=null && muldiv[k].getQk()!=null && muldiv[k].getCyclesRemaining()>0){
                        muldiv[k].setCyclesRemaining(muldiv[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<loadbuffers.length;k++){
                    if(loadbuffers[k].isBusy() && loadbuffers[k].getCyclesRemaining()>0){
                    loadbuffers[k].setCyclesRemaining(loadbuffers[k].getCyclesRemaining()-1);
                    }
                }
                for(int k=0;k<storebuffers.length;k++){
                    if(storebuffers[k].isBusy() && storebuffers[k].getQj()!=null && storebuffers[k].getCyclesRemaining()>0){
                        storebuffers[k].setCyclesRemaining(storebuffers[k].getCyclesRemaining()-1);
                    }
                }




            //Cycle printing
            System.out.println("After Cycle Number :" +cycles);
            cycles+=1;
            System.out.println("ADD/DIV Reservation Stations");
            System.out.println(addsub[0].toString());
            System.out.println(addsub[1].toString());
            System.out.println(addsub[2].toString());
            System.out.println("MUL/DIV Reservation Stations");
            System.out.println(muldiv[0].toString());
            System.out.println(muldiv[1].toString());
            System.out.println(muldiv[2].toString());
            System.out.println("Load Buffers");
            System.out.println(loadbuffers[0].toString());
            System.out.println(loadbuffers[1].toString());
            System.out.println(loadbuffers[2].toString());
            System.out.println("Store Buffers");
            System.out.println(storebuffers[0].toString());
            System.out.println(storebuffers[1].toString());
            System.out.println(storebuffers[2].toString());
            System.out.println("Register Files");
            for(int h=0;h<32;h+=4){
                System.out.print(Registers[h].toString());
                System.out.print(Registers[h+1].toString());
                System.out.print(Registers[h+2].toString());
                System.out.println(Registers[h+3].toString());
            }
            System.out.println("Memory");
            for (int o=0;o<memory.length;o+=32){
                System.out.print(memory[o]+",");
                System.out.print(memory[o+1]+",");
                System.out.print(memory[o+2]+",");
                System.out.print(memory[o+3]+",");
                System.out.print(memory[o+4]+",");
                System.out.print(memory[o+5]+",");
                System.out.print(memory[o+6]+",");
                System.out.print(memory[o+7]+",");
                System.out.print(memory[o+8]+",");
                System.out.print(memory[o+9]+",");
                System.out.print(memory[o+10]+",");
                System.out.print(memory[o+11]+",");
                System.out.print(memory[o+12]+",");
                System.out.print(memory[o+13]+",");
                System.out.print(memory[o+14]+",");
                System.out.print(memory[o+15]+",");
                System.out.print(memory[o+16]+",");
                System.out.print(memory[o+17]+",");
                System.out.print(memory[o+18]+",");
                System.out.print(memory[o+19]+",");
                System.out.print(memory[o+20]+",");
                System.out.print(memory[o+21]+",");
                System.out.print(memory[o+22]+",");
                System.out.print(memory[o+23]+",");
                System.out.print(memory[o+24]+",");
                System.out.print(memory[o+25]+",");
                System.out.print(memory[o+26]+",");
                System.out.print(memory[o+27]+",");
                System.out.print(memory[o+28]+",");
                System.out.print(memory[o+29]+",");
                System.out.print(memory[o+30]+",");
                System.out.println(memory[o+31]+", ");

            }
            System.out.println("_________________________________________________________");
            System.out.println("_________________________________________________________");
            System.out.println("_________________________________________________________");
            //Write back
            //Writing results from add-sub stations
            for(int l=0;l<addsub.length;l++){
                if(addsub[l].isBusy() && addsub[l].getCyclesRemaining()==0){
                    String name=addsub[l].getName();
                    double value=0;
                    if(addsub[l].getOpcode().equals("ADD")){
                        value=Double.parseDouble(addsub[l].getQj())+Double.parseDouble(addsub[l].getQk());
                    }else if(addsub[l].getOpcode().equals("SUB")){
                        value=Double.parseDouble(addsub[l].getQj())-Double.parseDouble(addsub[l].getQk());
                    }
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    addsub[l].setBusy(false);
                    addsub[l].setOpcode(null);
                    addsub[l].setQk(null);
                    addsub[l].setQj(null);
                    addsub[l].setVk(null);
                    addsub[l].setVj(null);
                    addsub[l].setCyclesRemaining(addsubcycles);
                }

            }
            //writing results from mul-div
            for(int l=0;l<muldiv.length;l++){
                if(muldiv[l].isBusy() && muldiv[l].getCyclesRemaining()==0) {
                    String name = muldiv[l].getName();
                    double value = 0;
                    if (muldiv[l].getOpcode().equals("MUL")) {
                        value = Double.parseDouble(muldiv[l].getQj()) * Double.parseDouble(muldiv[l].getQk());
                    } else if (muldiv[l].getOpcode().equals("DIV")) {
                        value = Double.parseDouble(muldiv[l].getQj()) / Double.parseDouble(muldiv[l].getQk());
                    }
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    muldiv[l].setBusy(false);
                    muldiv[l].setOpcode(null);
                    muldiv[l].setQk(null);
                    muldiv[l].setQj(null);
                    muldiv[l].setVk(null);
                    muldiv[l].setVj(null);
                    muldiv[l].setCyclesRemaining(muldivcycles);
                }
            }

            //writing results from load
            for(int l=0;l<loadbuffers.length;l++){
                if(loadbuffers[l].isBusy() && loadbuffers[l].getCyclesRemaining()==0) {
                    String name = loadbuffers[l].getName();
                    double value = memory[loadbuffers[l].getAddress()];
                    // writing in ADD-SUB
                    for(int k=0;k<addsub.length;k++){
                        if(addsub[k].isBusy()){
                            if(addsub[k].getVj()!=null && addsub[k].getVj().equals(name)){
                                addsub[k].setVj(null);
                                addsub[k].setQj(String.valueOf(value));
                            }
                            if(addsub[k].getVk()!=null && addsub[k].getVk().equals(name)){
                                addsub[k].setVk(null);
                                addsub[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in MUL-DIV
                    for(int k=0;k<muldiv.length;k++){
                        if(muldiv[k].isBusy()){
                            if(muldiv[k].getVj()!=null && muldiv[k].getVj().equals(name)){
                                muldiv[k].setVj(null);
                                muldiv[k].setQj(String.valueOf(value));
                            }
                            if(muldiv[k].getVk()!=null && muldiv[k].getVk().equals(name)){
                                muldiv[k].setVk(null);
                                muldiv[k].setQk(String.valueOf(value));
                            }
                        }
                    }
                    //writing in Store
                    for(int k=0;k<storebuffers.length;k++){
                        if(storebuffers[k].isBusy()){
                            if(storebuffers[k].getVj()!=null && storebuffers[k].getVj().equals(name)){
                                storebuffers[k].setVj(null);
                                storebuffers[k].setQj(String.valueOf(value));
                            }
                        }
                    }
                    //Writing in Register
                    for(int k=0;k<Registers.length;k++){
                        if(Registers[k].getValue().equals(name)){
                            Registers[k].setValue(value);
                        }
                    }
                    loadbuffers[l].setBusy(false);
                    loadbuffers[l].setCyclesRemaining(loadcylces);
                    loadbuffers[l].setAddress(0);
                }

            }


            for(int l=0;l<storebuffers.length;l++){
                if(storebuffers[l].isBusy() && storebuffers[l].getCyclesRemaining()==0){
                    double value =Double.parseDouble(storebuffers[l].getQj());
                    memory[storebuffers[l].getAddress()]=value;

                    storebuffers[l].setBusy(false);
                    storebuffers[l].setCyclesRemaining(storecycles);
                    storebuffers[l].setAddress(0);
                    storebuffers[l].setQj(null);
                    storebuffers[l].setVj(null);
                }
            }
            busyStation=false;
            for(int a =0;a<addsub.length;a++){
                if(addsub[a].isBusy()==true){
                    busyStation=true;
                    break;
                }
            }
            for(int a =0;a<muldiv.length;a++){
                if(muldiv[a].isBusy()==true){
                    busyStation=true;
                    break;
                }
            }
            for(int a=0;a<loadbuffers.length;a++){
                if(loadbuffers[a].isBusy()==true){
                    busyStation=true;
                    break;
                }
            }
            for(int a=0;a<storebuffers.length;a++){
                if(storebuffers[a].isBusy()==true){
                    busyStation=true;
                    break;
                }
            }
        }
        System.out.println("After Cycle Number :" +cycles);
        System.out.println("ADD/DIV Reservation Stations");
        System.out.println(addsub[0].toString());
        System.out.println(addsub[1].toString());
        System.out.println(addsub[2].toString());
        System.out.println("MUL/DIV Reservation Stations");
        System.out.println(muldiv[0].toString());
        System.out.println(muldiv[1].toString());
        System.out.println(muldiv[2].toString());
        System.out.println("Load Buffers");
        System.out.println(loadbuffers[0].toString());
        System.out.println(loadbuffers[1].toString());
        System.out.println(loadbuffers[2].toString());
        System.out.println("Store Buffers");
        System.out.println(storebuffers[0].toString());
        System.out.println(storebuffers[1].toString());
        System.out.println(storebuffers[2].toString());
        System.out.println("Register Files");
        for(int h=0;h<32;h+=4){
            System.out.print(Registers[h].toString());
            System.out.print(Registers[h+1].toString());
            System.out.print(Registers[h+2].toString());
            System.out.println(Registers[h+3].toString());
        }
        System.out.println("Memory");
        for (int o=0;o<memory.length;o+=32){
            System.out.print(memory[o]+",");
            System.out.print(memory[o+1]+",");
            System.out.print(memory[o+2]+",");
            System.out.print(memory[o+3]+",");
            System.out.print(memory[o+4]+",");
            System.out.print(memory[o+5]+",");
            System.out.print(memory[o+6]+",");
            System.out.print(memory[o+7]+",");
            System.out.print(memory[o+8]+",");
            System.out.print(memory[o+9]+",");
            System.out.print(memory[o+10]+",");
            System.out.print(memory[o+11]+",");
            System.out.print(memory[o+12]+",");
            System.out.print(memory[o+13]+",");
            System.out.print(memory[o+14]+",");
            System.out.print(memory[o+15]+",");
            System.out.print(memory[o+16]+",");
            System.out.print(memory[o+17]+",");
            System.out.print(memory[o+18]+",");
            System.out.print(memory[o+19]+",");
            System.out.print(memory[o+20]+",");
            System.out.print(memory[o+21]+",");
            System.out.print(memory[o+22]+",");
            System.out.print(memory[o+23]+",");
            System.out.print(memory[o+24]+",");
            System.out.print(memory[o+25]+",");
            System.out.print(memory[o+26]+",");
            System.out.print(memory[o+27]+",");
            System.out.print(memory[o+28]+",");
            System.out.print(memory[o+29]+",");
            System.out.print(memory[o+30]+",");
            System.out.println(memory[o+31]+", ");

        }
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
    }
}