function [ acc, rate ]= learningRate( n )
%LEARNINGRATE Summary of this function goes here
%   Detailed explanation goes here
%   [ acc, rate ]= learningRate(i), Level1.Q5
    acc = zeros(1, n);
    rate = zeros(1, n);
    load breast;
    for i = 1:n
        model = logreg('iterations',100, 'learningrate',0.01 + 0.01*i).train(data,labels);
        acc(i) = model.test(data, labels).acc();
        rate(i) = model.learningrate;
    end
end

