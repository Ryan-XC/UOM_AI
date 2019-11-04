%RBF SVM with various gamma value. Level1.Q5
laod heart
mysampler = sampler(data,labels);
[tr, te] = mysampler.split(1,5);
model = svm('-t 2 -g 1');%RBF svm with gamma=1
model.train(tr.data,tr.labels);
err = model.test(te.data,te.labels).err();%get error rate.
acc = model.test(data,labels).acc();