import matplotlib.pyplot as plt
import os

#file_path = os.path.join('my_folder', 'my_file.txt')
#file = open(file_path, 'r')
files=["l4w16p4f4m1.txt","l100w1p4f4m1.txt","l100w1p8f4m1.txt","l200w1p8f6m1.txt","l300w1p16f6m2.txt"]
for x in files:
   f=open(x,"r")
   line=f.readline()
   gen=[]
   gen_num=0
   gen_best=[]
   while len(gen)<=30:
      while not line.startswith("Generation"):
         line=f.readline()
      line=f.readline()
      gen.append(gen_num)
      gen_num+=1
      best=0
      while not line.startswith("Generation"):
         line=f.readline()
         if(line.startswith("Mean")):
            s=line.split(" ")
            number=float(s[-1])
            if number>best:
               best=number
      gen_best.append(best)

   print(gen)
   print(gen_best)

   plt.figure(figsize=(7,3))
   plt.plot(gen, gen_best)
   plt.xlabel('Generations')
   plt.ylabel('Best scores')
   plt.show()
