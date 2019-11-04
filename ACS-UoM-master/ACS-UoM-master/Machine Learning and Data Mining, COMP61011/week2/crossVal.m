function [ res ] = crossVal( range )
%CROSSVAL Summary of this function goes here Level1.Q1
%   Detailed explanation goes here
    load heart
    mysampler = sampler(data, labels);
    res = zeros(1,range);
    for i = 1:range
        [tr, te] = mysampler.split(i,5);
        model = logreg('iterations', 1, 'learningrate', 0.1).train(tr.data,tr.labels);
        temp = model.test(te.data, te.labels).err();
        res(i) =  temp;
    end
    res = std(res);
    res = mean(res);
end

