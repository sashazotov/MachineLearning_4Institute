%fileToWrite = fopen('c:\tmp\testFile.arff', 'w');

if(~isempty(handles.filename) && ~isempty(handles.filepath))
    fileToWrite = fopen(strcat(handles.filepath, handles.filename), 'w');
else
    set(handles.filePathName_edt, 'string', 'FILE NAME WAS NOT SELECTED');
    guidata(hObject, handles);
end

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