%visualize performance using plotboundry Level1.Q6 
load dataa;
mysampler = sampler(data,labels);
[tr, te] = mysampler.split(1,5);
model = svm('libsvm_options');
model.train(tr.data,tr.labels);
plotboundary(tr.data,tr.labels,model)