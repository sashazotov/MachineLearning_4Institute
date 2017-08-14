function varargout = createARFF(varargin)
% CREATEARFF MATLAB code for createARFF.fig
%      CREATEARFF, by itself, creates a new CREATEARFF or raises the existing
%      singleton*.
%
%      H = CREATEARFF returns the handle to a new CREATEARFF or the handle to
%      the existing singleton*.
%
%      CREATEARFF('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in CREATEARFF.M with the given input arguments.
%
%      CREATEARFF('Property','Value',...) creates a new CREATEARFF or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before createARFF_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to createARFF_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help createARFF

% Last Modified by GUIDE v2.5 13-Aug-2017 16:32:59

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @createARFF_OpeningFcn, ...
                   'gui_OutputFcn',  @createARFF_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before createARFF is made visible.
function createARFF_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to createARFF (see VARARGIN)

% Choose default command line output for createARFF
handles.output = hObject;


set(handles.attributes_tbl, 'ColumnEditable', true(1,10));
% Update handles structure
guidata(hObject, handles);

% UIWAIT makes createARFF wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = createARFF_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function filePathName_edt_Callback(hObject, eventdata, handles)
% hObject    handle to filePathName_edt (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of filePathName_edt as text
%        str2double(get(hObject,'String')) returns contents of filePathName_edt as a double


% --- Executes during object creation, after setting all properties.
function filePathName_edt_CreateFcn(hObject, eventdata, handles)
% hObject    handle to filePathName_edt (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in browse_btn.
function browse_btn_Callback(hObject, eventdata, handles)
% hObject    handle to browse_btn (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
    [filename, filepath, filter] = uiputfile('All Files');
    handles.filename = filename;
    handles.filepath = filepath;
    
    set(handles.filePathName_edt, 'string', strcat(filepath, filename));
    
    guidata(hObject, handles);
    


% --- Executes during object creation, after setting all properties.
function data_tbl_CreateFcn(hObject, eventdata, handles)
% hObject    handle to data_tbl (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called
    set(hObject, 'ColumnEditable', true(1,10));
    set(hObject, 'Data', cell(20, 6));
    
    guidata(hObject, handles);
    


% --- Executes on button press in save_btn.
function save_btn_Callback(hObject, eventdata, handles)
% hObject    handle to save_btn (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
    attributes = get(handles.attributes_tbl, 'data');
    attributes = attributes(~cellfun('isempty',attributes));
    data = get(handles.data_tbl, 'data');
    %data = data(~cellfun('isempty',data));
    saveHeader;
    saveData;
    writeFile;

% --- Executes during object creation, after setting all properties.
function attributes_tbl_CreateFcn(hObject, eventdata, handles)
% hObject    handle to attributes_tbl (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

    set(hObject, 'ColumnEditable', true(1,10));
    set(hObject, 'Data', cell(1,6));
    
    guidata(hObject, handles);
