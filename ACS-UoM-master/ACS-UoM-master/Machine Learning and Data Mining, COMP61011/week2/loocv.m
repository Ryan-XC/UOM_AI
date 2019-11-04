function [ err ] = loocv()
%LOOCV Summary of this function goes here. Level1.Q2
%   Detailed explanation goes here
    load heart
    times = numel(size(data));
    mysampler = sampler(data, labels);
    [tr, te] = mysampler.split(1,times);
    model = logreg('iterations', 1, 'learningrate', 0.1).train(tr.data,tr.labels);
    err = model.test(te.data, te.labels).err();
end

