load datag
mysampler = sampler(data,labels);
[tr, te] = mysampler.split(1,5);
%parameters for tree. *Always Straight segment* Depend on features.
tree = dtree('minex', 80).train(tr.data,tr.labels);
treeErr = model.test(te.data,te.labels).err();%tree.view() tree.
plotboundary(tr.data, tr.labels, tree)


model = logreg('iterations',100, 'learningrate',0.01);
model.train(tr.data,tr.labels);
logErr = model.test(te.data,te.labels).err();
plotboundary(tr.data, tr.labels, model)

