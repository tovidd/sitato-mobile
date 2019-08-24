<?php

namespace sitato\Http\Controllers;

use sitato\pelanggan;
use Illuminate\Http\Request;

class pelangganController extends Controller
{
    public function index() 
    {
        $data= pelanggan::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new pelanggan;
        $data->nama_pelanggan= $request->nama_pelanggan;
        $data->no_telepon_pelanggan= $request->no_telepon_pelanggan;
        $data->alamat_pelanggan= $request->alamat_pelanggan;
        
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
        $data= pelanggan::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(pelanggan $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= pelanggan::find($id);
        if(!is_null($request->nama_pelanggan))
        {
            $data->nama_pelanggan= $request->nama_pelanggan;
        }
        if(!is_null($request->no_telepon_pelanggan))
        {
            $data->no_telepon_pelanggan= $request->no_telepon_pelanggan;
        }
        if(!is_null($request->alamat_pelanggan))
        {
            $data->alamat_pelanggan= $request->alamat_pelanggan;
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
        $data= pelanggan::find($id);
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
