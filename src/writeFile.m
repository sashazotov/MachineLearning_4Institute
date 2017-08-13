fileToWrite = fopen('c:\tmp\testFile.arff', 'w');

for ii = 1:size(header(~cellfun('isempty',header)),1);
    if(~isempty(header{ii}))
        fprintf(fileToWrite, header{ii});
        fprintf(fileToWrite, '\n');
    else
        fprintf(fileToWrite, '\n');
    end
end

for ii = 1:size(dataToSave(~cellfun('isempty',dataToSave)),1);
    fprintf(fileToWrite, dataToSave{ii});
    fprintf(fileToWrite, '\n');
end