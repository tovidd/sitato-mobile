<?php

namespace sitato\Http\Controllers;

use sitato\statusPengerjaan;
use Illuminate\Http\Request;

class statusPengerjaanController extends Controller
{
    public function index() 
    {
        $data= statusPengerjaan::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new statusPengerjaan;
        $data->nama_status_pengerjaan= $request->nama_status_pengerjaan;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= statusPengerjaan::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(statusPengerjaan $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= statusPengerjaan::find($id);
        if(!is_null($request->nama_status_pengerjaan))
        {
            $data->nama_status_pengerjaan= $request->nama_status_pengerjaan;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= statusPengerjaan::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
