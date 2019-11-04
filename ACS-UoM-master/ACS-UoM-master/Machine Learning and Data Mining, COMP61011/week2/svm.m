%svm dataset training. Level1.Q4
help svm
load heart %wiz different datasets.
model = svm('libsvm_options');
mysampler = sampler(data,labels);
[tr, te] = mysampler.split(1,5);
err = model.test(te.data, te.labels).err();%ge.t error rate
